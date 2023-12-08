package com.mhs.kotlintest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mhs.kotlintest.R
import com.mhs.kotlintest.databinding.ActivityDetailsBinding
import com.mhs.kotlintest.utils.DataStatus
import com.mhs.kotlintest.utils.isVisible
import com.mhs.kotlintest.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val viewModel: MainViewModel by viewModels()
    private var itemURL: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize view binding
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the character's URL from the Intent
        itemURL = intent.getStringExtra("itemURL")
        if (!itemURL.isNullOrEmpty()) {
            // Extract character ID from the URL and fetch details
            val characterID = extractCharacterIDFromUrl(itemURL!!)
            if (characterID != null) {
                getCharacterDetails(characterID)
            }
        }
    }

    private fun getCharacterDetails(characterID: Int) {
        lifecycleScope.launch {
            binding.apply {
                viewModel.getCharacterDetails(characterID)
                viewModel.characterDetails.observe(this@DetailsActivity) { characterDetailsData ->
                    when (characterDetailsData.status) {
                        DataStatus.Status.LOADING -> {
                            pBarLoading.isVisible(true, mainLayout)
                        }

                        DataStatus.Status.SUCCESS -> {
                            pBarLoading.isVisible(false, mainLayout)
                            details = characterDetailsData.data // Bind character details to the layout
                            executePendingBindings() // Ensure bindings are updated
                        }

                        DataStatus.Status.ERROR -> {
                            pBarLoading.isVisible(false, mainLayout)
                            Toast.makeText(
                                this@DetailsActivity,
                                "There is something wrong!!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}

private fun extractCharacterIDFromUrl(url: String): Int? {
    // Define a regular expression to match numeric values
    val regex = "\\d+".toRegex()

    // Find the first match in the URL
    val matchResult = regex.find(url)

    // Extract the matched value and convert it to Int
    return matchResult?.value?.toIntOrNull()
}
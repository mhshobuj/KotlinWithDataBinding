package com.mhs.kotlintest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mhs.kotlintest.R
import com.mhs.kotlintest.adapter.CharacterListAdapter
import com.mhs.kotlintest.databinding.ActivityMainBinding
import com.mhs.kotlintest.utils.DataStatus
import com.mhs.kotlintest.utils.isVisible
import com.mhs.kotlintest.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private lateinit var characterListAdapter: CharacterListAdapter
    private val page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        characterListAdapter = CharacterListAdapter() // Initialize the adapter

        binding.recyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        binding.recyclerView.adapter = characterListAdapter

        lifecycleScope.launch {
            binding.apply {
                viewModel.getCharacterList(page)
                viewModel.characterList.observe(this@MainActivity){
                    when(it.status){
                        DataStatus.Status.LOADING ->{
                            pBarLoading.isVisible(true, recyclerView)
                        }

                        DataStatus.Status.SUCCESS ->{
                            // Pass retrieved data to the adapter
                            pBarLoading.isVisible(false, recyclerView)
                            characterListAdapter.submitList(it.data!!.results)
                        }

                        DataStatus.Status.ERROR ->{
                            pBarLoading.isVisible(false, recyclerView)
                            Toast.makeText(this@MainActivity, "There is something wrong!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

    }
}
package com.mhs.slider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mhs.kotlintest.R

class SliderActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var sliderAdapter: SliderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_slider)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        val imageUrls = listOf(
            "12,454",
            "43,345",
            "60,454"
        )

        sliderAdapter = SliderAdapter(imageUrls)
        viewPager.adapter = sliderAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.customView = createCustomTabView(position == 0) // Highlight the first tab initially
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val circle = tab?.customView?.findViewById<ImageView>(R.id.circle)
                val rectangle = tab?.customView?.findViewById<ImageView>(R.id.rectangle)
                circle?.visibility = View.GONE
                rectangle?.visibility = View.VISIBLE
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val circle = tab?.customView?.findViewById<ImageView>(R.id.circle)
                val rectangle = tab?.customView?.findViewById<ImageView>(R.id.rectangle)
                circle?.visibility = View.VISIBLE
                rectangle?.visibility = View.GONE
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun createCustomTabView(isSelected: Boolean): View {
        val view = LayoutInflater.from(this).inflate(R.layout.custom_tab, null)
        val circleImageView = view.findViewById<ImageView>(R.id.circle)
        val rectangleImageView = view.findViewById<ImageView>(R.id.rectangle)
        if (isSelected) {
            circleImageView.visibility = View.GONE
            rectangleImageView.visibility = View.VISIBLE
        } else {
            circleImageView.visibility = View.VISIBLE
            rectangleImageView.visibility = View.GONE
        }
        return view
    }
}
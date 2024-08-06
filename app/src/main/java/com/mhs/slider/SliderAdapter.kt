package com.mhs.slider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mhs.kotlintest.R

class SliderAdapter(private val imageUrls: List<String>) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val imageUrl = imageUrls[position]
        holder.imageView.text = imageUrl
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: TextView = itemView.findViewById(R.id.tvUpcomingPayment)
    }
}

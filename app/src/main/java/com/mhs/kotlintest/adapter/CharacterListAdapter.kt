package com.mhs.kotlintest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mhs.kotlintest.databinding.RecyclerItemBinding
import com.mhs.kotlintest.response.CharacterList

class CharacterListAdapter : ListAdapter<CharacterList.Result, CharacterListAdapter.MyViewHolder>(CharacterDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharacterList.Result) {
            binding.character = item
        }
    }
}

class CharacterDiffUtil : DiffUtil.ItemCallback<CharacterList.Result>(){
    override fun areItemsTheSame(oldItem: CharacterList.Result, newItem: CharacterList.Result): Boolean {
        return  oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: CharacterList.Result, newItem: CharacterList.Result): Boolean {
        return oldItem == newItem
    }
}

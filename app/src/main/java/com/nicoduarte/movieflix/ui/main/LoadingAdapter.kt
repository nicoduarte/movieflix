package com.nicoduarte.movieflix.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.databinding.ItemLoadingBinding
import com.nicoduarte.movieflix.ui.utils.inflate

class LoadingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return MovieHolder(parent.inflate(R.layout.item_loading))
    }

    override fun getItemCount() = 1


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieHolder).bind()
    }

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLoadingBinding.bind(itemView)

        fun bind() = with(binding) {

        }
    }
}
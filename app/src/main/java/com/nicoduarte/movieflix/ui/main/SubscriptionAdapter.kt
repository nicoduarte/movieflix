package com.nicoduarte.movieflix.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.ui.utils.inflate

class SubscriptionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return MoviewHolder(parent.inflate(R.layout.item_movie_subscription))
    }

    override fun getItemCount() = 10


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MoviewHolder).bind()
    }

    inner class MoviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
        ) = with(itemView) {

        }
    }
}
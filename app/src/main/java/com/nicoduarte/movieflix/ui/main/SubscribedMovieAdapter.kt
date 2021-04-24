package com.nicoduarte.movieflix.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.databinding.ItemInnerRecyclerBinding
import com.nicoduarte.movieflix.ui.utils.gone
import com.nicoduarte.movieflix.ui.utils.inflate
import com.nicoduarte.movieflix.ui.utils.visible

class SubscribedMovieAdapter(
    private var itemsSubscribed: MutableList<Movie>,
    private val clickListener: (Movie) -> Unit
) : RecyclerView.Adapter<SubscribedMovieAdapter.BaseHolder<*>>()  {

    fun addSubscribedMovies(movies: List<Movie>) {
        itemsSubscribed = movies.toMutableList()
        notifyItemChanged(0)
    }

    inner class MovieSubscriptionHolder(itemView: View) : BaseHolder<List<Movie>>(itemView) {
        private val binding = ItemInnerRecyclerBinding.bind(itemView)

        init {
            binding.rvMoviesSubscription.adapter = SubscriptionAdapter(mutableListOf()) {
                clickListener(it)
            }
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(binding.rvMoviesSubscription)
        }

        public override fun bind(data: List<Movie>) = with(binding) {
            if(data.isEmpty()) {
                root.gone()
                root.layoutParams = RecyclerView.LayoutParams(0, 0)
            }
            else {
                root.layoutParams = RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                root.visible()
                (rvMoviesSubscription.adapter as SubscriptionAdapter)
                    .addMovies(data)
            }
        }
    }

    abstract inner class BaseHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        protected abstract fun bind(data: T)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<*> {
        return MovieSubscriptionHolder(parent.inflate(viewType))
    }

    override fun onBindViewHolder(holder: BaseHolder<*>, position: Int) {
        (holder as MovieSubscriptionHolder).bind(itemsSubscribed)
    }

    override fun getItemCount(): Int = 1

    override fun getItemViewType(position: Int): Int {
        return ITEM_SUBSCRIPTION
    }

    companion object {
        private const val ITEM_SUBSCRIPTION = R.layout.item_inner_recycler
    }
}
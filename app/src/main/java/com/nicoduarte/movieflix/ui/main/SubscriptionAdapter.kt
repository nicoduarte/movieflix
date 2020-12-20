package com.nicoduarte.movieflix.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.api.ApiService
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.databinding.ItemMovieSubscriptionBinding
import com.nicoduarte.movieflix.ui.utils.inflate
import com.nicoduarte.movieflix.ui.utils.loadImage

class SubscriptionAdapter(
    private var items: MutableList<Movie>,
    private val clickListener: (Movie) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return MovieHolder(parent.inflate(R.layout.item_movie_subscription))
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieHolder).bind(items[position])
    }

    fun addMovies(data: List<Movie>) {
        items = data.toMutableList()
        notifyDataSetChanged()
    }

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieSubscriptionBinding.bind(itemView)

        fun bind(movie: Movie) = with(binding) {
            if (!movie.posterPath.isNullOrEmpty()) {
                ivCover.loadImage(
                    ApiService.IMAGE_BASE_URL.plus(movie.posterPath),
                    R.drawable.placeholder_movie
                )
            }
            root.setOnClickListener { clickListener(movie) }
        }
    }
}
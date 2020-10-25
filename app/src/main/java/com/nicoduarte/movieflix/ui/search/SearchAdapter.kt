package com.nicoduarte.movieflix.ui.search

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.api.ApiService
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.ui.utils.inflate
import com.nicoduarte.movieflix.ui.utils.loadImage
import kotlinx.android.synthetic.main.item_search_movie.view.*

class SearchAdapter(
    private var movieList: MutableList<Movie>,
    private val clickListener: (Movie) -> Unit
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return MovieHolder(parent.inflate(R.layout.item_search_movie))
    }

    override fun getItemCount() = movieList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieHolder).bind(movieList[position])
    }

    fun addMovies(list: List<Movie>) {
        notifyItemRangeRemoved(0, movieList.size)
        movieList = list.toMutableList()
        notifyItemRangeInserted(0, movieList.size)
    }

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) = with(itemView) {
            tvTitle.text = movie.title
            tvGenre.text = movie.genre?.name
            ivPoster.loadImage(
                ApiService.IMAGE_BASE_URL.plus(movie.posterPath),
                R.drawable.placeholder_movie
            )
            setOnClickListener { clickListener(movie) }
        }
    }
}
package com.nicoduarte.movieflix.ui.search

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
        movieList.clear()
        notifyDataSetChanged()
        movieList.addAll(list)
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

            if(movie.isSubscribed) {
                btnAdd.setBackgroundResource(R.drawable.bg_btn_added)
                btnAdd.setTextColor(ContextCompat.getColor(btnAdd.context, R.color.black_background))
                btnAdd.setText(R.string.added_movie)
            }
            else {
                btnAdd.setBackgroundResource(R.drawable.bg_btn_add)
                btnAdd.setTextColor(ContextCompat.getColor(btnAdd.context, R.color.white_30))
                btnAdd.setText(R.string.add_movie)
            }

            setOnClickListener { clickListener(movie) }
            btnAdd.setOnClickListener {
                movie.isSubscribed = !movie.isSubscribed
                movieList[adapterPosition] = movie
                notifyItemChanged(adapterPosition)
            }
        }
    }
}
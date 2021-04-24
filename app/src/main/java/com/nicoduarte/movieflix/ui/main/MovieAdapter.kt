package com.nicoduarte.movieflix.ui.main

import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.api.ApiService
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.databinding.ItemMainMovieBinding
import com.nicoduarte.movieflix.databinding.ItemTitleBinding
import com.nicoduarte.movieflix.ui.utils.gone
import com.nicoduarte.movieflix.ui.utils.inflate
import com.nicoduarte.movieflix.ui.utils.loadImage

class MovieAdapter(
    private var items: MutableList<Movie>,
    private val clickListener: (Movie) -> Unit
)
    : RecyclerView.Adapter<MovieAdapter.BaseHolder<*>>()  {

    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<*> {
        return when (viewType) {
            ITEM_TITLE -> TitleHolder(parent.inflate(viewType))
            ITEM_MOVIE -> MovieHolder(parent.inflate(viewType))
            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> ITEM_TITLE
            else -> ITEM_MOVIE
        }
    }

    override fun onBindViewHolder(holder: BaseHolder<*>, position: Int) {
        when(getItemViewType(position)) {
            ITEM_TITLE -> (holder as TitleHolder).bind("")
            ITEM_MOVIE -> {
                (holder as MovieHolder).bind(items[position])
                holder.setAnimation(holder.itemView, position)
            }
            else -> throw IllegalArgumentException("Invalid ViewHolder")
        }
    }

    override fun onViewDetachedFromWindow(holder: BaseHolder<*>) {
        super.onViewDetachedFromWindow(holder)
        if(holder is MovieHolder) holder.clearAnimation()
    }

    fun addMovies(movies: List<Movie>) {
        if(items.isNotEmpty()) {
            val positionStart = items.size
            items.addAll(movies)
            notifyItemRangeInserted(positionStart, items.size)
        } else {
            items.addAll(movies)
            notifyItemRangeInserted(0, items.size)
        }
    }

    inner class TitleHolder(itemView: View) : BaseHolder<String>(itemView) {
        private val binding = ItemTitleBinding.bind(itemView)

        public override fun bind(data: String) = with(itemView) {}
    }

    inner class MovieHolder(itemView: View) : BaseHolder<Movie>(itemView) {
        private val binding = ItemMainMovieBinding.bind(itemView)

        fun setAnimation(viewToAnimate: View, position: Int) {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition) {
                val animation = AnimationUtils.loadAnimation(
                    viewToAnimate.context,
                    android.R.anim.slide_in_left
                )
                viewToAnimate.startAnimation(animation)
                lastPosition = position
            }
        }

        fun clearAnimation() {
            itemView.clearAnimation()
        }

        public override fun bind(data: Movie): Unit = with(binding)  {
            tvName.text = data.title
            data.genre?.let {
                if (it.name.isEmpty()) tvCategory.gone()
                else tvCategory.text = data.genre?.name
            }
            if (!data.backdropPath.isNullOrEmpty()) {
                ivCover.loadImage(
                    ApiService.IMAGE_BASE_URL.plus(data.backdropPath),
                    R.drawable.placeholder_movie
                )
            }
            root.setOnClickListener { clickListener(data) }
        }
    }

    abstract inner class BaseHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        protected abstract fun bind(data: T)
    }

    companion object {
        private const val ITEM_TITLE = R.layout.item_title
        private const val ITEM_MOVIE = R.layout.item_main_movie
    }
}
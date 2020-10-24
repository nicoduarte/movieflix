package com.nicoduarte.movieflix.ui.detail

import android.graphics.Bitmap
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.api.ApiService
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.ui.BaseActivity
import com.nicoduarte.movieflix.ui.GlideApp
import com.nicoduarte.movieflix.ui.utils.colorPalette
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        movie?.let { loadMovie(it) }
    }

    private fun loadMovie(movie: Movie) {
//        ivPoster.loadImage(ApiService.IMAGE_BASE_URL.plus(movie.posterPath), R.drawable.placeholder_movie)
        tvTitle.text = movie.title
        tvReleaseDate.text = movie.releaseDate
        tvOverview.text = movie.overview

        GlideApp.with(this)
                .asBitmap()
                .load(ApiService.IMAGE_BASE_URL.plus(movie.posterPath))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder_movie)
                .listener(object : RequestListener<Bitmap?> {
                    override fun onLoadFailed(@Nullable e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Bitmap?>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: com.bumptech.glide.request.target.Target<Bitmap?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        resource?.let {
                            val palette = Palette.from(resource).generate()
                            palette.dominantSwatch?.rgb.let {
                                ivBackground.setBackgroundColor(ivBackground.colorPalette(it!!, 0.62f))
                            }
                        }
                        return false
                    }
                })
                .into(ivPoster)

//        val palette = Palette.from(bitmap).generate()
//        val target = Target.VIBRANT
//        val selectedSwatch = palette[target]
    }

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }
}
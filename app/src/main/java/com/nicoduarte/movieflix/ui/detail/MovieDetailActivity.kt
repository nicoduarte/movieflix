package com.nicoduarte.movieflix.ui.detail

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.appbar.AppBarLayout
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.api.ApiService
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.ui.BaseActivity
import com.nicoduarte.movieflix.ui.GlideApp
import com.nicoduarte.movieflix.ui.utils.colorPalette
import com.nicoduarte.movieflix.ui.utils.getDateFormatted
import com.nicoduarte.movieflix.ui.utils.loadImage
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetailActivity : BaseActivity(), AppBarLayout.OnOffsetChangedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        toolbarToLoad(toolbar)
        enableHomeDisplay(true)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        movie?.let { loadMovie(it) }

        appBar.addOnOffsetChangedListener(this)
        btnSubscribe.setOnClickListener { }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val offsetAlpha = appBarLayout.y / appBarLayout.totalScrollRange
        val scaleDownX = ObjectAnimator.ofFloat(cvPoster, "scaleX", offsetAlpha + 1)
        val scaleDownY = ObjectAnimator.ofFloat(cvPoster, "scaleY", offsetAlpha + 1)
        scaleDownX.setDuration(0).start()
        scaleDownY.setDuration(0).start()
    }

    private fun loadMovie(movie: Movie) {
        tvTitle.text = movie.title
        tvReleaseDate.text = getDateFormatted(movie.releaseDate, getString(R.string.format_origin))
        tvOverview.text = movie.overview

        GlideApp.with(this)
                .asBitmap()
                .load(ApiService.IMAGE_BASE_URL.plus(movie.posterPath))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder_movie)
                .listener(object : RequestListener<Bitmap?> {
                    override fun onLoadFailed(
                        @Nullable e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Bitmap?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Bitmap?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        resource?.let {
                            val palette = Palette.from(resource).generate()
                            palette.dominantSwatch?.rgb?.let {
                                ivBackground.setBackgroundColor(
                                    ivBackground.colorPalette(it)
                                )
                                collapsingToolbar.setContentScrimColor(it)
                            }

                            palette.lightVibrantSwatch?.rgb?.let {
                                tvTitle.setTextColor(it)
                                tvReleaseDate.setTextColor(it)
                                tvOverview.setTextColor(it)
                                tvLabelOverview.setTextColor(it)
                                btnSubscribe.backgroundTintList = ColorStateList.valueOf(it)
                            }

                            palette.darkVibrantSwatch?.rgb?.let {
                                btnSubscribe.setTextColor(it)
                            }
                        }
                        return false
                    }
                })
                .into(ivPoster)

        ivPosterBackground.loadImage(
            ApiService.IMAGE_BASE_URL.plus(movie.posterPath),
            R.drawable.placeholder_movie
        )
    }

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }
}
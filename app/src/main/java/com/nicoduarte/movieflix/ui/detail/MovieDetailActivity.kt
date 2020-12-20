package com.nicoduarte.movieflix.ui.detail

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.core.view.ViewCompat
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.appbar.AppBarLayout
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.api.ApiService
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.databinding.ActivityMovieDetailBinding
import com.nicoduarte.movieflix.ui.BaseActivity
import com.nicoduarte.movieflix.ui.GlideApp
import com.nicoduarte.movieflix.ui.utils.colorPalette
import com.nicoduarte.movieflix.ui.utils.getDateFormatted
import com.nicoduarte.movieflix.ui.utils.loadImage


class MovieDetailActivity : BaseActivity(), AppBarLayout.OnOffsetChangedListener {

    private val viewModelFactory by lazy { ViewModelFactory(application) }
    private val viewModel by viewModels<DetailViewModel> { viewModelFactory }
    private val binding by lazy { ActivityMovieDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        toolbarToLoad(binding.toolbar)
        enableHomeDisplay(true)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        movie?.let { loadMovie(it) }

        binding.appBar.addOnOffsetChangedListener(this)
        binding.btnSubscribe.setOnClickListener {
            movie?.let {
                movie.isSubscribed = !movie.isSubscribed
                viewModel.insertOrDelete(movie)
                changeTextButton(movie.isSubscribed)
            }
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val offsetAlpha = appBarLayout.y / appBarLayout.totalScrollRange
        when {
            offsetAlpha == 0f -> {
                ViewCompat.setNestedScrollingEnabled(binding.nestedScrollView, true)
            }
            offsetAlpha > -0.5 -> {
                val scaleDownX = ObjectAnimator.ofFloat(binding.cvPoster, "scaleX", offsetAlpha + 1)
                val scaleDownY = ObjectAnimator.ofFloat(binding.cvPoster, "scaleY", offsetAlpha + 1)
                scaleDownX.setDuration(0).start()
                scaleDownY.setDuration(0).start()

                val alphaTitle = ObjectAnimator.ofFloat(binding.tvTitle, "alpha", offsetAlpha + 1)
                val alphaDate = ObjectAnimator.ofFloat(binding.tvReleaseDate, "alpha", offsetAlpha + 1)
                alphaTitle.setDuration(0).start()
                alphaDate.setDuration(0).start()
            }
            else -> ViewCompat.setNestedScrollingEnabled(binding.nestedScrollView, false)
        }
    }

    private fun loadMovie(movie: Movie) {
        binding.tvTitle.text = movie.title
        binding.tvReleaseDate.text = getDateFormatted(movie.releaseDate, getString(R.string.format_origin))
        binding.tvOverview.text = movie.overview

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
                                binding.ivBackground.setBackgroundColor(
                                    colorPalette(it)
                                )
                                binding.collapsingToolbar.setContentScrimColor(it)
                            }

                            palette.lightVibrantSwatch?.rgb?.let {
                                binding.tvTitle.setTextColor(it)
                                binding.tvReleaseDate.setTextColor(it)
                                binding.tvOverview.setTextColor(it)
                                binding.tvLabelOverview.setTextColor(it)
                                binding.btnSubscribe.backgroundTintList = ColorStateList.valueOf(it)
                            }

                            palette.darkVibrantSwatch?.rgb?.let {
                                binding.btnSubscribe.setTextColor(it)
                            }
                        }
                        return false
                    }
                })
                .into(binding.ivPoster)

        binding.ivPosterBackground.loadImage(
            ApiService.IMAGE_BASE_URL.plus(movie.posterPath),
            R.drawable.placeholder_movie
        )

        changeTextButton(movie.isSubscribed)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
    }

    private fun changeTextButton(isSubscribed: Boolean) {
        if (isSubscribed) {
            binding.btnSubscribe.text = getString(R.string.btn_subscribed)
        } else {
            binding.btnSubscribe.text = getString(R.string.btn_subscribe)
        }
    }

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }
}
package com.nicoduarte.movieflix.ui.detail

import android.os.Bundle
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.ui.BaseActivity

class MovieDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
    }

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }
}
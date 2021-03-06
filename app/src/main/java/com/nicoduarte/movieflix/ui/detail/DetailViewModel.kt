package com.nicoduarte.movieflix.ui.detail

import androidx.lifecycle.ViewModel
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.ui.main.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailViewModel(private val repository: MovieRepository) : ViewModel() {

    fun insertOrDelete(movie: Movie) {
        if(movie.isSubscribed)
            repository.insertMovie(movie)
                .subscribe(this::onSuccessDelete, this::onError)
        else
            repository.delete(movie)
                .subscribe(this::onSuccessDelete, this::onError)
    }

    private fun onSuccessDelete() {

    }

    private fun onSuccessDelete(id: Int) {

    }

    private fun onError(error: Throwable) {

    }
}
package com.nicoduarte.movieflix.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nicoduarte.movieflix.database.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import com.nicoduarte.movieflix.api.Result

class MainViewModel(
    application: Application,
    private val movieRepository: MovieRepository
) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    private val movies = MutableLiveData<Result<List<Movie>>>()

    init { getMovies() }

    private fun getMovies() {
        movies.postValue(Result.loading())
        val disposable = movieRepository.getMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onSuccessMovies, this::onErrorMovies)
        compositeDisposable.add(disposable)
    }

    private fun onErrorMovies(t: Throwable) {
        movies.postValue(Result.error(message = t.message))
    }

    private fun onSuccessMovies(list: List<Movie>) {
        movies.postValue(Result.success(list))
    }

    fun getMoviesLiveData(): LiveData<Result<List<Movie>>> = movies

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
package com.nicoduarte.movieflix.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicoduarte.movieflix.database.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import com.nicoduarte.movieflix.api.Result

class MainViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val movies = MutableLiveData<Result<List<Movie>>>()

    init { getMovies() }

    fun getMovies(page: Int = 1) {
        movies.postValue(Result.loading())
        val disposable = movieRepository.getMovies(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onSuccessMovies, this::onErrorMovies)
        compositeDisposable.add(disposable)
    }

    private fun onErrorMovies(error: Throwable) {
        movies.postValue(Result.error(message = error.message))
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
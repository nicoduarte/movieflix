package com.nicoduarte.movieflix.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicoduarte.movieflix.api.Result
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.ui.main.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val moviesLiveData = MutableLiveData<Result<List<Movie>>>()

    fun getMovieLiveData(): LiveData<Result<List<Movie>>> = moviesLiveData

    fun searchMovies(query: String) {
        moviesLiveData.postValue(Result.loading())
        if(query.isNotEmpty()) {
            val disposable = repository.searchMovies(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onSuccessMovies, this::onErrorMovies)

            compositeDisposable.add(disposable)
        }
    }

    private fun onSuccessMovies(movies: List<Movie>) {
        moviesLiveData.postValue(Result.success(movies))
    }

    private fun onErrorMovies(error: Throwable) {
        moviesLiveData.postValue(Result.error(message = error.message))
    }

    fun insertOrDelete(movie: Movie) {
        if(movie.isSubscribed)
            repository.insertMovie(movie)
                .subscribe(this::onSuccessDelete, this::onErrorMovies)
        else
            repository.delete(movie)
                .subscribe(this::onSuccessDelete, this::onErrorMovies)
    }

    private fun onSuccessDelete() {}

    private fun onSuccessDelete(id: Int) {}
}

package com.nicoduarte.movieflix.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.api.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val moviesLiveData = MutableLiveData<Result<List<Movie>>>()
    private val moviesSubscribed = MutableLiveData<Result<List<Movie>>>()

    init {
        getMovies()
        getSubscribedMovies()
    }

    private fun getSubscribedMovies() = viewModelScope.launch {
        val movies = withContext(Dispatchers.IO) { movieRepository.getSubscribedMovies() }
        moviesSubscribed.value = Result.success(movies)
    }

    fun getMovies(page: Int = 1) = viewModelScope.launch {
        moviesLiveData.value = Result.loading()
        val moviesResponse = withContext(Dispatchers.IO) { movieRepository.getMovies(page) }
        val genresResponse = async { movieRepository.getGenres() }

        val movies = moviesResponse.movies
        val genres = genresResponse.await().genres

        movies.forEach { movie ->
            if (!movie.genreIds.isNullOrEmpty())
                movie.genre = genres.find { movie.genreIds!!.first() == it.id }
        }

        moviesLiveData.value = Result.success(movies)
    }

    private fun onErrorMovies(error: Throwable) {
        moviesLiveData.postValue(Result.error(message = error.message))
    }

    fun getMoviesLiveData(): LiveData<Result<List<Movie>>> = moviesLiveData

    fun getMoviesSubscribedLiveData(): LiveData<Result<List<Movie>>> = moviesSubscribed
}

package com.nicoduarte.movieflix.ui.main

import androidx.lifecycle.*
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.api.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val moviesLiveData = MutableLiveData<Result<MoviesUiModel>>()
    val moviesSubscribed = movieRepository.getSubscribedMovies()
            .map { Result.success(it) }
            .asLiveData()

    init { getMovies() }

    fun getMovies(page: Int = 1) = viewModelScope.launch {
        if (page == 1) {
            moviesLiveData.value = Result.loading()
        } else {
            moviesLiveData.value = Result.success(MoviesUiModel(showLoading = true))
        }

        val moviesResponse = withContext(Dispatchers.IO) { movieRepository.getMovies(page) }
        val genresResponse = async { movieRepository.getGenres() }

        val movies = moviesResponse.movies
        val genres = genresResponse.await().genres

        movies.forEach { movie ->
            if (!movie.genreIds.isNullOrEmpty())
                movie.genre = genres.find { movie.genreIds!!.first() == it.id }
        }

        if (page > 1) {
            moviesLiveData.value = Result.success(MoviesUiModel(pageMovies = movies))
        } else {
            moviesLiveData.value = Result.success(MoviesUiModel(firstMovies = movies))
        }

    }

    private fun onErrorMovies(error: Throwable) {
        moviesLiveData.postValue(Result.error(message = error.message))
    }

    fun getMoviesLiveData(): LiveData<Result<MoviesUiModel>> = moviesLiveData

    data class MoviesUiModel(
        val firstMovies: List<Movie>? = null,
        val pageMovies: List<Movie>? = null,
        val showLoading: Boolean = false
    )
}

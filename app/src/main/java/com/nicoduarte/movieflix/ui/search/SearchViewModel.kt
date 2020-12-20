package com.nicoduarte.movieflix.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicoduarte.movieflix.api.Result
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.ui.main.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val moviesLiveData = MutableLiveData<Result<List<Movie>>>()

    fun getMovieLiveData(): LiveData<Result<List<Movie>>> = moviesLiveData

    fun searchMovies(query: String) = viewModelScope.launch {
        moviesLiveData.value = Result.loading()
        val moviesResponse = withContext(Dispatchers.IO) { repository.searchMovies(query) }
        val genresResponse = async { repository.getGenres() }

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

    fun insertOrDelete(movie: Movie) = viewModelScope.launch {
        if(movie.isSubscribed) repository.insertMovie(movie)
        else repository.delete(movie)
    }
}

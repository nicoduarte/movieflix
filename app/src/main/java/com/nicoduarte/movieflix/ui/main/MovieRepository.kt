package com.nicoduarte.movieflix.ui.main

import android.app.Application
import com.nicoduarte.movieflix.api.ApiService
import com.nicoduarte.movieflix.api.response.GenreResponse
import com.nicoduarte.movieflix.api.response.MovieResponse
import com.nicoduarte.movieflix.database.GenreDao
import com.nicoduarte.movieflix.database.MovieDao
import com.nicoduarte.movieflix.database.MovieDatabase
import com.nicoduarte.movieflix.database.model.Movie

class MovieRepository(
    application: Application
) {
    private val movieDao: MovieDao
    private val genreDao: GenreDao

    init {
        val movieDatabase = MovieDatabase.getDatabase(application)
        movieDao = movieDatabase.movieDao()
        genreDao = movieDatabase.genreDao()
    }

    suspend fun getMovies(page: Int = 1): MovieResponse {
        return ApiService.getInstance().getUpcoming(ApiService.API_KEY, page)
    }

    suspend fun getGenres(): GenreResponse {
        return ApiService.getInstance().getGenreList(ApiService.API_KEY)
    }

    suspend fun searchMovies(query: String): MovieResponse {
        return ApiService.getInstance().searchMovies(ApiService.API_KEY, query)
    }

    suspend fun insertMovie(movie: Movie) {
        return movieDao.insert(movie)
    }

    suspend fun delete(movie: Movie) {
        return movieDao.delete(movie)
    }

    suspend fun getSubscribedMovies(): List<Movie> {
        return movieDao.getMovies()
    }
}
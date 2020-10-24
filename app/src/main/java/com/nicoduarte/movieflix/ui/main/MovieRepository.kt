package com.nicoduarte.movieflix.ui.main

import android.app.Application
import com.nicoduarte.movieflix.api.ApiService
import com.nicoduarte.movieflix.database.model.Movie
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class MovieRepository(
    application: Application
) {
    fun getMovies(page: Int = 1): Observable<List<Movie>> {
        val genresRequest = ApiService.getInstance()
                .getGenreList(ApiService.API_KEY)

        val moviesRequest = ApiService.getInstance()
                .getUpcoming(ApiService.API_KEY, page)

        return Observable.zip(genresRequest, moviesRequest,
                BiFunction { genres, list ->
            val movies = list.movies
            movies.forEach { movie ->
                movie.genre = genres.genres.find { movie.genreIds?.first() == it.id }
            }
            return@BiFunction movies
        })
    }
}
package com.nicoduarte.movieflix.ui.main

import android.app.Application
import com.nicoduarte.movieflix.api.ApiService
import com.nicoduarte.movieflix.database.GenreDao
import com.nicoduarte.movieflix.database.MovieDao
import com.nicoduarte.movieflix.database.MovieDatabase
import com.nicoduarte.movieflix.database.model.Genre
import com.nicoduarte.movieflix.database.model.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

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

    fun getMovies(page: Int = 1): Observable<List<Movie>> {
        val genresRequest = ApiService.getInstance()
                .getGenreList(ApiService.API_KEY)

        val moviesRequest = ApiService.getInstance()
                .getUpcoming(ApiService.API_KEY, page)

        return Observable.zip(genresRequest, moviesRequest,
            BiFunction { genresResponse, moviesResponse ->
                val movies = moviesResponse.movies
                movies.forEach { movie ->
                    if (!movie.genreIds.isNullOrEmpty())
                        movie.genre =
                            genresResponse.genres.find { movie.genreIds?.first() == it.id }
                    else movie.genre = Genre(1, "")
                }
                return@BiFunction movies
            })
    }

    fun searchMovies(query: String): Observable<List<Movie>> {
        val genresRequest = ApiService.getInstance()
            .getGenreList(ApiService.API_KEY)

        val moviesRequest = ApiService.getInstance()
            .searchMovies(ApiService.API_KEY, query)

        return Observable.zip(genresRequest, moviesRequest,
            BiFunction { genresResponse, moviesResponse ->
                val movies = moviesResponse.movies
                movies.forEach { movie ->
                    if (!movie.genreIds.isNullOrEmpty())
                        movie.genre =
                            genresResponse.genres.find { movie.genreIds?.first() == it.id }
                }
                return@BiFunction movies
            })
    }

    fun insertMovie(movie: Movie): Completable {
        return movieDao.insert(movie)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun delete(movie: Movie): Single<Int> {
        return movieDao.delete(movie)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun getSubscribedMovies(): Flowable<List<Movie>> {
        return movieDao.getMovies()
    }
}
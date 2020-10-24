package com.nicoduarte.movieflix.api

import com.nicoduarte.movieflix.api.response.MovieResponse
import com.nicoduarte.movieflix.api.response.GenreResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {

    @GET("movie/upcoming")
    fun getUpcoming(@Query("api_key") apiKey: String, @Query("page") page: Int): Observable<MovieResponse>

    @GET("genre/movie/list")
    fun getGenreList(@Query("api_key") apiKey: String): Observable<GenreResponse>

    @GET("search/movie")
    fun searchMovies(@Query("api_key") apiKey: String, @Query("query") query: String): Observable<MovieResponse>


}
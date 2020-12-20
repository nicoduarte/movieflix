package com.nicoduarte.movieflix.api

import com.nicoduarte.movieflix.api.response.GenreResponse
import com.nicoduarte.movieflix.api.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {

    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("api_key") apiKey: String, @Query("page") page: Int): MovieResponse

    @GET("genre/movie/list")
    suspend fun getGenreList(@Query("api_key") apiKey: String): GenreResponse

    @GET("search/movie")
    suspend fun searchMovies(@Query("api_key") apiKey: String, @Query("query") query: String): MovieResponse


}
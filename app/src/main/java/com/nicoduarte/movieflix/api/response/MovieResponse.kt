package com.nicoduarte.movieflix.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.nicoduarte.movieflix.database.model.Movie

class MovieResponse {

    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("results")
    @Expose
    lateinit var movies: List<Movie>
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null

}

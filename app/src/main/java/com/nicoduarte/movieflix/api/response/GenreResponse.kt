package com.nicoduarte.movieflix.api.response

import com.nicoduarte.movieflix.database.model.Genre

data class GenreResponse(val genres: List<Genre>)
package com.nicoduarte.movieflix.db


import com.nicoduarte.movieflix.database.model.Genre
import com.nicoduarte.movieflix.database.model.Movie

class TestData {

    companion object {
        private val MOVIE_ENTITY = Movie(
            1,
            title = "bumblebee",
            genre = Genre(1, "Accion"),
            genreIds = listOf(1, 2)
        )
        val MOVIE_ENTITY2 = Movie(
            2,
            title = "aquaman",
            genre = Genre(2, "Accion"),
            genreIds = listOf(1, 2)
        )

        val MOVIES = listOf(MOVIE_ENTITY, MOVIE_ENTITY2)
    }
}
package com.nicoduarte.movieflix.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nicoduarte.movieflix.database.model.Genre
import io.reactivex.Single

@Dao
interface GenreDao {
    @Insert
    fun insertAllGenres(genres: List<Genre>)

    @Query("SELECT * from genre_table")
    fun getGenres(): Single<List<Genre>>
}
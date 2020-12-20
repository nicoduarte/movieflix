package com.nicoduarte.movieflix.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nicoduarte.movieflix.database.model.Genre

@Dao
interface GenreDao {
    @Insert
    suspend fun insertAllGenres(genres: List<Genre>)

    @Query("SELECT * from genre_table")
    suspend fun getGenres(): List<Genre>
}
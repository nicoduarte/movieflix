package com.nicoduarte.movieflix.database

import androidx.room.*
import com.nicoduarte.movieflix.database.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * from movie_table ORDER BY vote_count DESC")
    suspend fun getMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("SELECT * from movie_table WHERE title LIKE '%' || :query || '%'  ORDER BY vote_count DESC")
    suspend fun searchMovies(query: String): List<Movie>
}

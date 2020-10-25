package com.nicoduarte.movieflix.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nicoduarte.movieflix.database.model.Movie
import io.reactivex.Single

@Dao
interface MovieDao {
    @Query("SELECT * from movie_table ORDER BY vote_count DESC LIMIT 20")
    fun getMovies(category: Int): Single<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Query("SELECT * from movie_table WHERE title LIKE '%' || :query || '%'  ORDER BY vote_count DESC LIMIT 20")
    fun searchMovies(category: Int, query: String): Single<List<Movie>>
}

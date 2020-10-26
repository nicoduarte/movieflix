package com.nicoduarte.movieflix.database

import androidx.room.*
import com.nicoduarte.movieflix.database.model.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie): Completable

    @Delete
    fun delete(movie: Movie): Single<Int>

    @Query("SELECT * from movie_table ORDER BY vote_count DESC")
    fun getMovies(): Flowable<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Query("SELECT * from movie_table WHERE title LIKE '%' || :query || '%'  ORDER BY vote_count DESC")
    fun searchMovies(query: String): Single<List<Movie>>
}

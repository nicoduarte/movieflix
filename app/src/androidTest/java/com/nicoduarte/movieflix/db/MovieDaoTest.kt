package com.nicoduarte.movieflix.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nicoduarte.movieflix.database.MovieDao
import com.nicoduarte.movieflix.database.MovieDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var database: MovieDatabase
    private lateinit var movieDao: MovieDao

    @Before
    @Throws(Exception::class)
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            MovieDatabase::class.java
        )
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

        movieDao = database.movieDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        database.close()
    }


    @Test
    @Throws(InterruptedException::class)
    fun getMoviesAfterInserted() {
        // When inserting movies in the data source
        movieDao.insertAll(TestData.MOVIES)

        // When subscribing to the emissions of the movies
        movieDao.getMovies()
            .test()
            .assertValue { it.size ==  TestData.MOVIES.size}
    }


    @Test
    fun searchMoviesWhenMatchTitle() {
        // When inserting movies in the data source
        movieDao.insertAll(TestData.MOVIES)

        database.movieDao().searchMovies("aquaman")
            .test()
            .assertValue { it.size ==  1 && it[0].id == TestData.MOVIE_ENTITY2.id}
    }

}
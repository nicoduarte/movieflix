package com.nicoduarte.movieflix.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nicoduarte.movieflix.database.model.Genre
import com.nicoduarte.movieflix.database.model.Movie

@Database(entities = [Movie::class, Genre::class], version = 1)
@TypeConverters(DataConverter::class, GenreConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao

    companion object {
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                synchronized(MovieDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MovieDatabase::class.java, "movie_database"
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}

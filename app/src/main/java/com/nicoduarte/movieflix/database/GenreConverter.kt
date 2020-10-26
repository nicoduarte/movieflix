package com.nicoduarte.movieflix.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.nicoduarte.movieflix.database.model.Genre

class GenreConverter {
    @TypeConverter
    fun fromGenre(genre: Genre): String? = Gson().toJson(genre, Genre::class.java)

    @TypeConverter
    fun toGenre(genre: String?): Genre = Gson().fromJson(genre, Genre::class.java)
}
package com.nicoduarte.movieflix.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {
    @TypeConverter
    fun fromGenreList(genreList: List<Int>): String? {
        if (genreList.isNullOrEmpty()) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Int>?>() {}.type
        return gson.toJson(genreList, type)
    }

    @TypeConverter
    fun toGenreList(genres: String?): List<Int> {
        if (genres == null) {
            return emptyList()
        }
        val gson = Gson()
        val type = object : TypeToken<List<Int>?>() {}.type
        return gson.fromJson(genres, type)
    }

}
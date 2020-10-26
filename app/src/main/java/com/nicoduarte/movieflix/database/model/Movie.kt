package com.nicoduarte.movieflix.database.model

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "movie_table")
@Parcelize
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null,
    @ColumnInfo(name = "adult")
    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null,
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    @Expose
    var overview: String? = null,
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null,
    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null,
    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    var title: String? = null,
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null,
    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null,
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null,
    @ColumnInfo(name = "video")
    @SerializedName("video")
    @Expose
    var video: Boolean? = null,
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null,
    @ColumnInfo(name = "genre_ids")
    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int>? = null,
    @ColumnInfo(name = "genre")
    var genre: Genre? = null,
    @ColumnInfo(name = "is_subscribed")
    var isSubscribed: Boolean = false
): Parcelable

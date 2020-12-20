package com.nicoduarte.movieflix.api

import com.nicoduarte.movieflix.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService {

    companion object {
        private var apiRequest: ApiRequest? = null
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        const val API_KEY = "208ca80d1e219453796a7f9792d16776"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w300"

        fun getInstance(): ApiRequest {
            if (apiRequest == null) {
                val httpClient = getOkHttpClient()

                val builder = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)

                apiRequest = builder.build().create(ApiRequest::class.java)

            }
            return apiRequest!!
        }

        private fun getOkHttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) builder.addInterceptor(loggingInterceptor)

            return builder
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()
        }
    }
}
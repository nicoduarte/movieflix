package com.nicoduarte.movieflix.ui.search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicoduarte.movieflix.ui.main.MovieRepository

class ViewModelFactory(application: Application)
    : ViewModelProvider.NewInstanceFactory() {

    private val repository: MovieRepository = MovieRepository(application)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
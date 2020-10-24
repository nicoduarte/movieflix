package com.nicoduarte.movieflix.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val application: Application)
    : ViewModelProvider.NewInstanceFactory() {

    private val repository: MovieRepository = MovieRepository(application)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return MainViewModel(application, repository) as T
    }
}
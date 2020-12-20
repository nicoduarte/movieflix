package com.nicoduarte.movieflix.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.ui.main.MovieRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository) : ViewModel() {

    fun insertOrDelete(movie: Movie) {
        viewModelScope.launch {
            try {
                if(movie.isSubscribed) repository.insertMovie(movie)
                else repository.delete(movie)
            } catch (e: Exception) {

            }
        }
    }
}
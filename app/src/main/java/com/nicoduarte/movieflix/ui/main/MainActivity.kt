package com.nicoduarte.movieflix.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.api.Result
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.ui.BaseActivity
import com.nicoduarte.movieflix.ui.utils.EqualSpacingItemDecoration
import com.nicoduarte.movieflix.ui.utils.gone
import com.nicoduarte.movieflix.ui.utils.showMessage
import com.nicoduarte.movieflix.ui.utils.visible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val viewModelFactory by lazy { ViewModelFactory(application) }
    private val viewModel by viewModels<MainViewModel> {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbarToLoad(toolbar)
        setupList()
        viewModel.getMoviesLiveData().observe(this, { observerLiveData(it) })
    }

    private fun observerLiveData(result: Result<List<Movie>>) {
        result.setState({
            progress.gone()
            rvMovies.visible()
            (rvMovies.adapter as MovieAdapter).addMovies(it)
        },{
            showMessage(rootView, it)
        },{
            progress.visible()
            rvMovies.gone()
        })
    }

    private fun setupList() {
        rvMovies.adapter = MovieAdapter(mutableListOf()) {

        }
        rvMovies.addItemDecoration(
            EqualSpacingItemDecoration(
                32,
                EqualSpacingItemDecoration.VERTICAL
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
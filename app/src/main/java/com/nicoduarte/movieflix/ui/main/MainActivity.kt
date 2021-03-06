package com.nicoduarte.movieflix.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.api.Result
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.ui.BaseActivity
import com.nicoduarte.movieflix.ui.detail.MovieDetailActivity
import com.nicoduarte.movieflix.ui.search.SearchActivity
import com.nicoduarte.movieflix.ui.utils.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val viewModelFactory by lazy { ViewModelFactory(application) }
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbarToLoad(toolbar)
        setupList()
        viewModel.getMoviesLiveData().observe(this, { observerLiveData(it) })
        viewModel.getMoviesSubscribedLiveData().observe(this, { observerSubscribedLiveData(it) })
        btnTryAgain.setOnClickListener { viewModel.getMovies() }
    }

    private fun observerSubscribedLiveData(results: Result<List<Movie>>) {
        results.setState({
            (rvMovies.adapter as MovieAdapter).addSubscribedMovies(it)
        }, {
            showMessage(rootView, it)
        }, {})
    }

    private fun observerLiveData(results: Result<List<Movie>>) {
        results.setState({
            containerNoConexion.gone()
            rvMovies.visible()
            (rvMovies.adapter as MovieAdapter).addMovies(it)
        }, {
            containerNoConexion.visible()
            rvMovies.gone()
        }, {})
    }

    private fun setupList() {
        rvMovies.adapter = MovieAdapter(mutableListOf(), mutableListOf()) { goToDetail(it) }
        rvMovies.addItemDecoration(
            EqualSpacingItemDecoration(
                resources.getDimensionPixelOffset(R.dimen.margin_8dp),
                EqualSpacingItemDecoration.VERTICAL
            )
        )
        rvMovies.addOnScrollListener(
            object :
                EndlessRecyclerViewScrollListener(rvMovies.layoutManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.getMovies(page)
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                goToSearch()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToDetail(movie: Movie) {
        startActivity(
            Intent(
                this,
                MovieDetailActivity::class.java
            )
                .putExtra(MovieDetailActivity.EXTRA_MOVIE, movie)
        )
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
    }

    private fun goToSearch() {
        startActivity(Intent(this, SearchActivity::class.java))
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
    }
}
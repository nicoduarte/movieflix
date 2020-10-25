package com.nicoduarte.movieflix.ui.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.api.Result
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.ui.BaseActivity
import com.nicoduarte.movieflix.ui.detail.MovieDetailActivity
import com.nicoduarte.movieflix.ui.utils.EqualSpacingItemDecoration
import com.nicoduarte.movieflix.ui.utils.gone
import com.nicoduarte.movieflix.ui.utils.showMessage
import com.nicoduarte.movieflix.ui.utils.visible
import kotlinx.android.synthetic.main.activity_movie_detail.toolbar
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.progress

class SearchActivity : BaseActivity() {

    private val viewModelFactory by lazy { ViewModelFactory(application) }
    private val viewModel by viewModels<SearchViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        toolbarToLoad(toolbar)
        enableHomeDisplay(true)
        setupRecycler()

        viewModel.getMovieLiveData().observe(this, { observerLiveData(it) })
    }

    private fun setupRecycler() {
        rvSearchMovies.adapter = SearchAdapter(mutableListOf()) {
            startActivity(
                    Intent(this, MovieDetailActivity::class.java)
                            .putExtra(MovieDetailActivity.EXTRA_MOVIE, it)
            )
        }
        rvSearchMovies.addItemDecoration(
                EqualSpacingItemDecoration(
                        32,
                        EqualSpacingItemDecoration.VERTICAL
                ))
        rvSearchMovies.addItemDecoration(
                DividerItemDecoration(
                        this,
                        DividerItemDecoration.VERTICAL)
        )
    }

    private fun observerLiveData(result: Result<List<Movie>>) {
        result.setState({
            progress.gone()
            rvSearchMovies.visible()
            (rvSearchMovies.adapter as SearchAdapter).addMovies(it)
            if(it.isEmpty()) tvEmptyMovies.visible()
            else tvEmptyMovies.gone()
        },{
            showMessage(rootView, it)
            tvEmptyMovies.gone()
            tvEmptyMovies.visible()
        },{
            progress.visible()
            rvSearchMovies.gone()
            tvEmptyMovies.gone()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchItem.expandActionView()
        searchView.queryHint = getString(R.string.hint_search)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchMovies(query)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
        searchView.isIconified = false
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
    }
}
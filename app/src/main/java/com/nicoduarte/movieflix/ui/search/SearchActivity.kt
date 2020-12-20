package com.nicoduarte.movieflix.ui.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.api.Result
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.databinding.ActivitySearchBinding
import com.nicoduarte.movieflix.ui.BaseActivity
import com.nicoduarte.movieflix.ui.detail.MovieDetailActivity
import com.nicoduarte.movieflix.ui.utils.EqualSpacingItemDecoration
import com.nicoduarte.movieflix.ui.utils.gone
import com.nicoduarte.movieflix.ui.utils.showMessage
import com.nicoduarte.movieflix.ui.utils.visible
import kotlinx.android.synthetic.main.activity_movie_detail.toolbar

class SearchActivity : BaseActivity() {

    private val viewModelFactory by lazy { ViewModelFactory(application) }
    private val viewModel by viewModels<SearchViewModel> { viewModelFactory }
    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        toolbarToLoad(toolbar)
        enableHomeDisplay(true)
        setupRecycler()

        viewModel.getMovieLiveData().observe(this, { observerLiveData(it) })
    }

    private fun setupRecycler() {
        binding.rvSearchMovies.adapter = SearchAdapter(mutableListOf(), {
            goToDetail(it)
        }, {
            viewModel.insertOrDelete(it)
        })

        binding.rvSearchMovies.addItemDecoration(
                EqualSpacingItemDecoration(
                        resources.getDimensionPixelOffset(R.dimen.margin_8dp),
                        EqualSpacingItemDecoration.VERTICAL
                )
        )

        val divider = DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
        )

        divider.setDrawable(ShapeDrawable().apply {
            intrinsicHeight = resources.getDimensionPixelOffset(R.dimen.divider_height)
            paint.color = ContextCompat.getColor(this@SearchActivity, R.color.gray_divider)
        })

        binding.rvSearchMovies.addItemDecoration(divider)
    }

    private fun observerLiveData(result: Result<List<Movie>>) {
        result.setState({
            binding.progress.gone()
            binding.rvSearchMovies.visible()
            (binding.rvSearchMovies.adapter as SearchAdapter).setMovies(it)
            if(it.isEmpty()) binding.tvEmptyMovies.visible()
            else binding.tvEmptyMovies.gone()
        },{
            showMessage(binding.root, it)
            binding.progress.gone()
            binding.tvEmptyMovies.visible()
        },{
            binding.progress.visible()
            binding.rvSearchMovies.gone()
            binding.tvEmptyMovies.gone()
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

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
    }
}
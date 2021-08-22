package com.nicoduarte.movieflix.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.api.Result
import com.nicoduarte.movieflix.database.model.Movie
import com.nicoduarte.movieflix.databinding.ActivityMainBinding
import com.nicoduarte.movieflix.ui.BaseActivity
import com.nicoduarte.movieflix.ui.detail.MovieDetailActivity
import com.nicoduarte.movieflix.ui.search.SearchActivity
import com.nicoduarte.movieflix.ui.utils.*

class MainActivity : BaseActivity() {

    private val viewModelFactory by lazy { ViewModelFactory(application) }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        toolbarToLoad(binding.toolbar)
        setupList()
        viewModel.getMoviesLiveData().observe(this, { observerLiveData(it) })
        viewModel.moviesSubscribed.observe(this, { observerSubscribedLiveData(it) })
        binding.btnTryAgain.setOnClickListener { viewModel.getMovies() }
    }

    private fun observerSubscribedLiveData(results: Result<List<Movie>>) {
        results.setState({
            hideSkeleton()
            ((binding.rvMovies.adapter as ConcatAdapter)
                .adapters.first() as SubscribedMovieAdapter).addSubscribedMovies(it)
        }, {
            showMessage(binding.root, it)
        })
    }

    private fun observerLiveData(uiModel: Result<MainViewModel.MoviesUiModel>) {
        uiModel.setState({
            if (uiModel.data?.showLoading == true) {
                if ((binding.rvMovies.adapter as ConcatAdapter).adapters.size < 3) {
                    (binding.rvMovies.adapter as ConcatAdapter).addAdapter(LoadingAdapter())
                }
            } else {
                if ((binding.rvMovies.adapter as ConcatAdapter).adapters.size > 2) {
                    val removedAdapter = (binding.rvMovies.adapter as ConcatAdapter).adapters[2]
                    (binding.rvMovies.adapter as ConcatAdapter).removeAdapter(removedAdapter)
                }
            }

            uiModel.data?.firstMovies?.let {
                hideSkeleton()
                binding.containerNoConexion.gone()
                binding.rvMovies.visible()
                ((binding.rvMovies.adapter as ConcatAdapter)
                    .adapters[1] as MovieAdapter).addMovies(it)
            }

            uiModel.data?.pageMovies?.let {
                ((binding.rvMovies.adapter as ConcatAdapter)
                    .adapters[1] as MovieAdapter).addMovies(it)
            }
        }, {
            hideSkeleton()
            binding.containerNoConexion.visible()
            binding.rvMovies.gone()
        }, {
            showSkeleton()
        })
    }

    private fun setupList() {
        binding.rvMovies.adapter = initAdapter()
        binding.rvMovies.addItemDecoration(
            EqualSpacingItemDecoration(
                resources.getDimensionPixelOffset(R.dimen.margin_8dp),
                EqualSpacingItemDecoration.VERTICAL
            )
        )
        binding.rvMovies.addOnScrollListener(
            object :
                EndlessRecyclerViewScrollListener(binding.rvMovies.layoutManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.getMovies(page)
                }
            })
    }

    private fun initAdapter(): ConcatAdapter {
        val subscribedMovieAdapter = SubscribedMovieAdapter(mutableListOf()) { goToDetail(it) }
        val moviesAdapter =  MovieAdapter(mutableListOf()) { goToDetail(it) }
        return ConcatAdapter(subscribedMovieAdapter, moviesAdapter)
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

    private fun showSkeleton() {
        binding.viewSwitcher.displayedChild = SKELETON_INDEX
    }

    private fun hideSkeleton() {
        binding.viewSwitcher.displayedChild = CONTENT_INDEX
    }

    companion object {
        private const val SKELETON_INDEX = 0
        private const val CONTENT_INDEX = 1
    }
}
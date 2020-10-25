package com.nicoduarte.movieflix.ui.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.ui.BaseActivity
import com.nicoduarte.movieflix.ui.utils.EqualSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        toolbarToLoad(toolbar)
        enableHomeDisplay(true)

        rvSearchMovies.adapter = SearchAdapter(mutableListOf()) {

        }
        rvSearchMovies.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvSearchMovies.addItemDecoration(
            EqualSpacingItemDecoration(
            32,
            EqualSpacingItemDecoration.VERTICAL
        ))
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
//                searchViewModel.searchMovies(Category.ALL, query)
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
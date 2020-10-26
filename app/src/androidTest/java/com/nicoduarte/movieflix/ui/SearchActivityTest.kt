package com.nicoduarte.movieflix.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.nicoduarte.movieflix.ui.search.SearchActivity
import org.junit.Rule
import org.junit.runner.RunWith


@SmallTest
@RunWith(AndroidJUnit4::class)
class SearchActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(SearchActivity::class.java)

}
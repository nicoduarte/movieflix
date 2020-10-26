package com.nicoduarte.movieflix.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.ui.detail.MovieDetailActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@SmallTest
@RunWith(AndroidJUnit4::class)
class DetailActivityTest {

    @Rule
    var activityScenarioRule = ActivityScenarioRule(
        MovieDetailActivity::class.java
    )


    @Test
    fun clickOnSubscribeButton() {
        onView(ViewMatchers.withId(R.id.btnSubscribe))
            .perform(ViewActions.click())
    }
}
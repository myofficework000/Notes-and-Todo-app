package com.example.notesapp.ui_test

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import com.example.notesapp.R
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.notesapp.UITestUtils
import com.example.notesapp.view.fragments.DashboardFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TodoUiTest {
    private lateinit var fragment: FragmentScenario<DashboardFragment>

    @Before
    fun setUp() {
        fragment = launchFragmentInContainer(
            initialState = Lifecycle.State.CREATED,
            themeResId = R.style.Theme_NotesApp
        )
    }

    @Test
    fun recyclerViewIsVisible() {
        fragment.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.RVTodo)).check(matches(isDisplayed()))
    }

    @Test
    fun recyclerViewStartsEmpty() {
        fragment.moveToState(Lifecycle.State.STARTED)
        onView(withId(R.id.RVTodo)).check(matches(UITestUtils.recyclerViewSize(0)))
    }
}
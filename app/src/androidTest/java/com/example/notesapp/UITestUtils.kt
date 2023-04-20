package com.example.notesapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

object UITestUtils {
    fun recyclerViewSize(size: Int) =
        object: BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

            override fun matchesSafely(item: RecyclerView?) = (item?.adapter?.itemCount == size).also{println(item?.adapter?.itemCount);println(size)}
            override fun describeTo(description: Description?) {}
        }
}
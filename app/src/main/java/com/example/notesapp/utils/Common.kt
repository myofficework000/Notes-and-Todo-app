package com.example.notesapp.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService

object Common {
    fun hideKeyboard(activity: Activity?) {
        activity?.apply {
            if (currentFocus == null) return
            getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(
                currentFocus?.windowToken, 0
            )
        }
    }
}
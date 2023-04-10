package com.example.notesapp.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import com.example.notesapp.model.Constants

object Common {
    fun hideKeyboard(activity: Activity?) {
        activity?.apply {
            if (currentFocus == null) return
            getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(
                currentFocus?.windowToken, 0
            )
        }
    }

    fun stringInt(str: String?, isColorNo: Boolean = false): Int {
        if (str == null) return 0
        if (str.isEmpty()) return 0
        var intVal = str.toIntOrNull() ?: 0
        if (isColorNo) {
            if (intVal > Constants.SOFT_COLORS.size - 1) {
                intVal = Constants.SOFT_COLORS.size - 1
            }
        }
        return intVal
    }
}
package io.github.ytam.rickandmorty.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import java.net.URL
import okio.IOException
import timber.log.Timber

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.hideKeyboard() {
    try {
        val manager: InputMethodManager = context.applicationContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(windowToken, 0)
    } catch (e: java.lang.Exception) {
        Timber.i(e)
    }
}

fun TextView.trim(): String {
    return text!!.toString().trim()
}

fun TextView.trimOrNull(): String? {
    return text?.toString()?.trim()
}

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}

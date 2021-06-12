package io.github.ytam.rickandmorty.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.LayoutRes

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.visibleGone(predicate: () -> Boolean) {
    if (predicate.invoke()) visible() else gone()
}

fun View.visibleInvisible(predicate: () -> Boolean) {
    if (predicate.invoke()) visible() else invisible()
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.hideKeyboard() {
    try {
        val manager: InputMethodManager = context.applicationContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(windowToken, 0)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

fun TextView.trim(): String {
    return text!!.toString().trim()
}

fun TextView.trimOrNull(): String? {
    return text?.toString()?.trim()
}

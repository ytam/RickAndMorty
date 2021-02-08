package io.github.ytam.rickandmorty.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import io.github.ytam.rickandmorty.R

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
fun ImageView.loadFromUrl(url: String) {

    Glide.with(context)
        .load(url)
        .placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher_round)
        .into(this)
}

@BindingAdapter("android:loadUrl")
fun loadImage(view: ImageView, url: String?) {

    url?.let {
        view.loadFromUrl(it)
    }
}

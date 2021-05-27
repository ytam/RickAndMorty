package io.github.ytam.rickandmorty.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import io.github.ytam.rickandmorty.R

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
fun ImageView.loadFromUrl(url: String) {

    var options = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.progress_animation)
        .error(R.mipmap.ic_launcher_round)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .dontAnimate()
        .dontTransform()

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher_round)
        .apply(options)
        .into(this)
}

@BindingAdapter("android:loadUrl")
fun loadImage(view: ImageView, url: String?) {

    url?.let {
        view.loadFromUrl(it)
    }
}

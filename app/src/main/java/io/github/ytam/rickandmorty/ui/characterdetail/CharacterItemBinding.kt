package io.github.ytam.rickandmorty.ui.characterdetail

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import io.github.ytam.rickandmorty.R

class CharacterItemBinding {

    companion object {
        @BindingAdapter("applyStatusColor")
        @JvmStatic
        fun applyStatusColor(view: View, status: String) {

            when (status) {
                 "Alive" -> {
                     view.setBackgroundColor(
                         ContextCompat.getColor(view.context, R.color.design_default_color_secondary)
                     )
                }
                 "Dead" -> {
                     view.setBackgroundColor(
                         ContextCompat.getColor(view.context, R.color.design_default_color_secondary)
                     )
                }
                 "Unknown" -> {
                     view.setBackgroundColor(
                         ContextCompat.getColor(view.context, R.color.design_default_color_secondary)
                     )
                }
            }
        }

        @SuppressLint("ResourceAsColor")
        @BindingAdapter("app:tint")
        fun Chip.setBackgroundColor(status: String) {

            when(status){
                "Alive" -> {
                    setBackgroundColor(R.color.design_default_color_secondary)

                }
            }
        }
    }
}
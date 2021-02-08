package io.github.ytam.rickandmorty.model

import com.google.gson.annotations.SerializedName

/**
 *Created by Yıldırım TAM on 06/02/2021.
 */
data class Episode(

    @SerializedName("episode")
    val episode: String?,

    @SerializedName("air_date")
    val airDate: String?
)

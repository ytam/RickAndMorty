package io.github.ytam.rickandmorty.model

import com.google.gson.annotations.SerializedName

/**
 *Created by Yıldırım TAM on 06/02/2021.
 */
data class Origin(

    @SerializedName("name")
    val name: String?
)


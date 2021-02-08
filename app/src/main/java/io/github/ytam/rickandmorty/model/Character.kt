package io.github.ytam.rickandmorty.model

import com.google.gson.annotations.SerializedName

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
data class Character(

    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String?,
    @SerializedName("species")
    val species: String?,
    @SerializedName("image")
    val image: String?
)
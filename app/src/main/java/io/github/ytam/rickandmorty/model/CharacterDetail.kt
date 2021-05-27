package io.github.ytam.rickandmorty.model

import com.google.gson.annotations.SerializedName

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
data class CharacterDetail(

    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String?,
    @SerializedName("species")
    val species: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("origin")
    val origin: Origin?,
    @SerializedName("location")
    val location: Location?,
    @SerializedName("episode")
    val episode: List<String>?,
)

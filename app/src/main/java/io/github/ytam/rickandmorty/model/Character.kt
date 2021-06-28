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
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("image")
    val image: String?
)

data class Info(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("pages")
    val pages: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("prev")
    val prev: String?,
)

data class CharacterList(

    @SerializedName("info")
    val info: Info?,
    @SerializedName("results")
    val results: List<Character>?
)

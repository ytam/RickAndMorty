package io.github.ytam.rickandmorty.service.response

import com.google.gson.annotations.SerializedName

/**
 *Created by Yıldırım TAM on 08/02/2021.
 */
data class ResultInfo(

    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("prev")
    val prev: String?,
)

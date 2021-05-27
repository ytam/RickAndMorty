package io.github.ytam.rickandmorty.service.response

import com.google.gson.annotations.SerializedName
import io.github.ytam.rickandmorty.model.Character

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */

data class CharactersResponse(
    @SerializedName("results")
    val results: List<Character>,
    @SerializedName("info")
    val info: ResultInfo
)

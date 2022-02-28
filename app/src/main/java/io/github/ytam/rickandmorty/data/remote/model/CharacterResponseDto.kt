package io.github.ytam.rickandmorty.data.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterResponseDto(
    @SerializedName("info")
    val info: InfoDto,
    @SerializedName("results")
    val results: List<CharacterDto>
)

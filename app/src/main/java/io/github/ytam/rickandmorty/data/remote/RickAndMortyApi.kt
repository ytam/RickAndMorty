package io.github.ytam.rickandmorty.data.remote

import io.github.ytam.rickandmorty.data.remote.model.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character/")
    suspend fun getCharacters(
        @Query("page")
        page: Int
    ): CharacterResponseDto

    @GET("character/")
    suspend fun getCharactersByName(
        @Query("page")
        page: Int,
        @Query("name")
        characterName: String
    ): CharacterResponseDto
}

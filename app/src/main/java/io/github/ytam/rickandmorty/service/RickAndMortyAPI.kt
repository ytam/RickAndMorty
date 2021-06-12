package io.github.ytam.rickandmorty.service

import io.github.ytam.rickandmorty.model.CharacterDetail
import io.github.ytam.rickandmorty.service.response.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
interface RickAndMortyAPI {

    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page: String,
        @Query("name") name: String?
    ): CharactersResponse

    @GET("character/{id}")
    suspend fun getCharacterDetail(
        @Path("id") id: Int
    ): CharacterDetail
}

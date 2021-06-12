package io.github.ytam.rickandmorty.repository

import io.github.ytam.rickandmorty.model.Character
import io.github.ytam.rickandmorty.model.CharacterDetail
import io.github.ytam.rickandmorty.service.RickAndMortyAPI

class CharacterRepository(private val api: RickAndMortyAPI) {

    suspend fun getCharacters(page: Int, name: String?): List<Character> {
        return api.getCharacters(page.toString(), name).results
    }

    suspend fun getCharacterById(id: Int): CharacterDetail {
        return api.getCharacterDetail(id)
    }
}

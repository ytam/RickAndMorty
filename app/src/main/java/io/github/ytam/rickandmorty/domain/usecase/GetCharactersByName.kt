package io.github.ytam.rickandmorty.domain.usecase

import androidx.paging.PagingData
import io.github.ytam.rickandmorty.domain.model.Character
import io.github.ytam.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersByName(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(characterName: String): Flow<PagingData<Character>> =
        characterRepository.getCharactersByName(characterName)
}

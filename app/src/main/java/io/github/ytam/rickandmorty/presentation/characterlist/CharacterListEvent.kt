package io.github.ytam.rickandmorty.presentation.characterlist

sealed class CharacterListEvent {
    data class GetAllCharactersByName(val characterName: String) : CharacterListEvent()
}

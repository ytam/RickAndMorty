package io.github.ytam.rickandmorty.presentation.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.ytam.rickandmorty.domain.model.Character
import io.github.ytam.rickandmorty.domain.usecase.GetCharactersByName
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersByNameUseCase: GetCharactersByName
) : ViewModel() {
    private val _charactersFlow = MutableSharedFlow<PagingData<Character>>()
    val charactersFlow = _charactersFlow.asSharedFlow()

    private val _searchQuery: MutableStateFlow<String> =
        MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    private var searchJob: Job? = null

    companion object {
        const val TIME_MILLIS = 500L
    }

    fun onEvent(event: CharacterListEvent) {
        when (event) {
            is CharacterListEvent.GetAllCharactersByName -> onSearch(event.characterName)
        }
    }

    init {
        getCharactersByName("")
    }

    private fun getCharactersByName(characterName: String) {
        getCharactersByNameUseCase(characterName).onEach {
            _charactersFlow.emit(it)
        }.launchIn(viewModelScope)
    }

    private fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(TIME_MILLIS)
            getCharactersByName(query)
        }
    }
}

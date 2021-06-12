package io.github.ytam.rickandmorty.ui.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.ytam.rickandmorty.model.CharacterDetail
import io.github.ytam.rickandmorty.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
class CharacterDetailViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val characterDetail = MutableLiveData<CharacterDetail>()
    private val characterError = MutableLiveData<Throwable>()

    /**
     * @param id = Clicked character id
     * @return
     */
    fun getCharacterById(id: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching { repository.getCharacterById(id) }
            }
            result.onSuccess { characterDetail.value = it }
            result.onFailure { characterError.value = it }
        }
    }

    fun getCharacterLiveData(): LiveData<CharacterDetail> {
        return characterDetail
    }

    fun getErrorLiveData(): LiveData<Throwable> {
        return characterError
    }
}

package io.github.ytam.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ytam.rickandmorty.model.Character
import io.github.ytam.rickandmorty.service.RickAndMortyService
import io.github.ytam.rickandmorty.service.response.CharactersResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
class CharacterViewModel : ViewModel() {

    private val characterApiService = RickAndMortyService()
    private val disposable = CompositeDisposable()
    val characters = MutableLiveData<List<Character>>()
    val characterError = MutableLiveData<Boolean>()
    val characterLoading = MutableLiveData<Boolean>()
    val isNextPageAvailable = MutableLiveData<Boolean>()

    private var pageNumber: Int = 1

    /**
     * @param name = Search name text
     * @return
     */
    fun getDataFromAPI(name: String?) {

        disposable.add(
            characterApiService.getCharacters("1", name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CharactersResponse>() {
                    override fun onSuccess(t: CharactersResponse) {

                        isNextPageAvailable.value = t.info.next != null
                        characters.value = t.results
                        characterError.value = false
                    }

                    override fun onError(e: Throwable) {

                        characterError.value = true
                        e.printStackTrace()

                    }
                })
        )
    }

    fun searchNextPage(name: String?) {

        characterLoading.value = true
        pageNumber += 1

        disposable.add(
            characterApiService.getCharacters(pageNumber.toString(), name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CharactersResponse>() {
                    override fun onSuccess(t: CharactersResponse) {

                        //Check next page available or not
                        isNextPageAvailable.value = t.info.next != null

                        characters.value = characters.value.orEmpty() + t.results

                        characterLoading.value = false
                        characterError.value = false
                    }

                    override fun onError(e: Throwable) {

                        characterError.value = true

                        e.printStackTrace()
                    }
                })
        )
    }
}
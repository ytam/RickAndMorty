package io.github.ytam.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ytam.rickandmorty.model.CharacterDetail
import io.github.ytam.rickandmorty.service.RickAndMortyService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
class CharacterDetailViewModel : ViewModel() {

    private val characterApiService = RickAndMortyService()
    private val disposable = CompositeDisposable()
    val characterDetail = MutableLiveData<CharacterDetail>()
    val characterError = MutableLiveData<Boolean>()

    /**
     * @param id = Clicked character id
     * @return
     */
    fun getDataFromAPI(id: Int) {

        disposable.add(
            characterApiService.getCharacterDetail(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CharacterDetail>() {
                    override fun onSuccess(t: CharacterDetail) {

                        characterDetail.value = t
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
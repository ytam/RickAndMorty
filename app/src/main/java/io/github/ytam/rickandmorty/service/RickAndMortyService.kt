package io.github.ytam.rickandmorty.service

import io.github.ytam.rickandmorty.model.CharacterDetail
import io.github.ytam.rickandmorty.service.response.CharactersResponse
import io.github.ytam.rickandmorty.utils.Util.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
class RickAndMortyService {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(RickAndMortyAPI::class.java)

    /**
     * @param pageNumber
     */
    fun getCharacters(pageNumber: String, name: String?): Single<CharactersResponse> {

        println(pageNumber)
        return api.getCharacters(pageNumber, name)
    }

    /**
     * @param id
     */
    fun getCharacterDetail(id: Int): Single<CharacterDetail> {

        return api.getCharacterDetail(id)
    }
}
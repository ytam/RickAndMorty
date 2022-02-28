package io.github.ytam.rickandmorty.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.ytam.rickandmorty.data.locale.Database
import io.github.ytam.rickandmorty.data.remote.RickAndMortyApi
import io.github.ytam.rickandmorty.data.repository.CharacterRepositoryImpl
import io.github.ytam.rickandmorty.domain.repository.CharacterRepository
import io.github.ytam.rickandmorty.domain.usecase.GetCharactersByName
import io.github.ytam.rickandmorty.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RickAndMortyModule {
    @Singleton
    @Provides
    fun provideGetCharactersByNameUseCase(
        getCharactersRepository: CharacterRepository
    ): GetCharactersByName = GetCharactersByName(getCharactersRepository)

    @Singleton
    @Provides
    @ExperimentalPagingApi
    fun provideCharacterRepository(
        api: RickAndMortyApi,
        db: Database
    ): CharacterRepository = CharacterRepositoryImpl(api, db)

    @Singleton
    @Provides
    fun provideRickAndMortyApi(): RickAndMortyApi =
        Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        )
            .build().create(RickAndMortyApi::class.java)

    @Singleton
    @Provides
    fun provideRickAndMortyDatabase(
        app: Application
    ): Database =
        Room.databaseBuilder(app, Database::class.java, "rickAndMortyDb").build()
}

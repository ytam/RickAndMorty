package io.github.ytam.rickandmorty.di

import io.github.ytam.rickandmorty.repository.CharacterRepository
import io.github.ytam.rickandmorty.ui.character.CharacterViewModel
import io.github.ytam.rickandmorty.ui.characterdetail.CharacterDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { CharacterRepository(get()) }

    viewModel { CharacterViewModel(get()) }

    viewModel { CharacterDetailViewModel(get()) }
}

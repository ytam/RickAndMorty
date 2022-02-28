package io.github.ytam.rickandmorty.data.repository

import androidx.paging.*
import io.github.ytam.rickandmorty.data.locale.Database
import io.github.ytam.rickandmorty.data.paging.RemoteMediator
import io.github.ytam.rickandmorty.data.remote.RickAndMortyApi
import io.github.ytam.rickandmorty.domain.model.Character
import io.github.ytam.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalPagingApi
class CharacterRepositoryImpl(
    private val api: RickAndMortyApi,
    private val db: Database
) : CharacterRepository {
    override fun getCharactersByName(characterName: String): Flow<PagingData<Character>> {
        val pagingSourceFactory = { db.characterDao.getCharactersByName(characterName) }

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true,
            ),
            remoteMediator = RemoteMediator(
                api,
                db,
                characterName
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { CharacterEntityPagingData ->
            CharacterEntityPagingData.map { characterEntity -> characterEntity.toCharacter() }
        }
    }
}

package io.github.ytam.rickandmorty.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import io.github.ytam.rickandmorty.data.locale.Database
import io.github.ytam.rickandmorty.data.locale.entity.CharacterEntity
import io.github.ytam.rickandmorty.data.locale.entity.RemoteKeyEntity
import io.github.ytam.rickandmorty.data.remote.RickAndMortyApi
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class RemoteMediator(
    private val api: RickAndMortyApi,
    private val db: Database,
    private val characterName: String
) : RemoteMediator<Int, CharacterEntity>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as Int
        }

        try {
            val response = if (characterName.isEmpty())
                api.getCharacters(page = page)
            else api.getCharactersByName(page = page, characterName)

            val isEndOfList =

                response.info.next == null || response.toString().contains("error") || response.results.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.characterDao.deleteCharacters()
                    db.remoteKeyDao.deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.results.map {
                    RemoteKeyEntity(it.id, prevKey = prevKey, nextKey = nextKey)
                }
                db.remoteKeyDao.insertAll(keys)
                db.characterDao.insertCharacters(response.results.map { it.toCharacterEntity() })
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CharacterEntity>): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                db.remoteKeyDao.remoteKeysCharacterId(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, CharacterEntity>): RemoteKeyEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { character -> db.remoteKeyDao.remoteKeysCharacterId(character.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, CharacterEntity>): RemoteKeyEntity? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { character -> db.remoteKeyDao.remoteKeysCharacterId(character.id) }
    }
}

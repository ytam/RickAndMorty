package io.github.ytam.rickandmorty

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.ytam.rickandmorty.model.Character
import io.github.ytam.rickandmorty.service.RickAndMortyAPI

class CharacterPagingSource(val apiService: RickAndMortyAPI): PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val nextPage: Int = params.key ?: FIRST_PAGE_INDEX
            val response = apiService.getCharacters(nextPage.toString(),"")

            var nextPageNumber: Int? = null
            if(response?.info?.next != null) {
                val uri = Uri.parse(response?.info?.next!!)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }
            var prevPageNumber: Int? = null
            if(response?.info?.prev != null) {
                val uri = Uri.parse(response?.info?.prev!!)
                val prevPageQuery = uri.getQueryParameter("page")

                prevPageNumber = prevPageQuery?.toInt()
            }

            LoadResult.Page(data = response.results,
                prevKey = prevPageNumber,
                nextKey = nextPageNumber)
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {

        return state.anchorPosition
    }
}
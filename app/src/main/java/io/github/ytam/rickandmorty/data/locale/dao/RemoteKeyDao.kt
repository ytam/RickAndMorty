package io.github.ytam.rickandmorty.data.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.ytam.rickandmorty.data.locale.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_keys WHERE characterId = :id")
    suspend fun remoteKeysCharacterId(id: Int): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()
}

package io.github.ytam.rickandmorty.data.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.ytam.rickandmorty.data.locale.dao.CharacterDao
import io.github.ytam.rickandmorty.data.locale.dao.RemoteKeyDao
import io.github.ytam.rickandmorty.data.locale.entity.CharacterEntity
import io.github.ytam.rickandmorty.data.locale.entity.RemoteKeyEntity

@Database(entities = [CharacterEntity::class, RemoteKeyEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract val characterDao: CharacterDao
    abstract val remoteKeyDao: RemoteKeyDao
}

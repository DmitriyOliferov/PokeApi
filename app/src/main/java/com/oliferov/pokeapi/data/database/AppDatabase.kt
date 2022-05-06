package com.oliferov.pokeapi.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PokemonDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase() : RoomDatabase() {

    companion object {
        private const val DB_NAME = "pokemon.db"
        private var db: AppDatabase? = null
        private val LOCK = Any()
    }


//    fun getInstance(): AppDatabase {
//        synchronized(LOCK) {
//            db?.let { return it }
//            val instance = Room.databaseBuilder(
//                application,
//                AppDatabase::class.java,
//                DB_NAME
//            )
//                .fallbackToDestructiveMigration()
//                .build()
//            db = instance
//            return instance
//        }
//    }

    abstract fun getPokemonDao(): PokemonDao
}
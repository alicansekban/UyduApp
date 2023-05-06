package com.alican.mvvm_starter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alican.mvvm_starter.data.local.model.SatelliteModel

@Database(entities = [SatelliteModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): AppDao
}

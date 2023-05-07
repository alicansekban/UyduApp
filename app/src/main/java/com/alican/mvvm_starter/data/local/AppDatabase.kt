package com.alican.mvvm_starter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.alican.mvvm_starter.data.local.model.SatelliteDetailEntity
import com.alican.mvvm_starter.data.local.model.SatelliteModel
import com.alican.mvvm_starter.data.local.model.SatellitePositionsEntity
import com.alican.mvvm_starter.data.model.SatelliteDetailModel
import com.google.gson.Gson

@Database(entities = [SatelliteModel::class, SatelliteDetailEntity::class], version = 2)
@TypeConverters(SatellitePositionsTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun satelliteDao(): SatelliteDao
    abstract fun satelliteDetailDao(): SatelliteDetailDao
}

class SatellitePositionsTypeConverter {
    @TypeConverter
    fun stringToList(value: String): SatellitePositionsEntity {
        return Gson().fromJson(value, SatellitePositionsEntity::class.java)
    }

    @TypeConverter
    fun listToString(user: SatellitePositionsEntity): String {
        return Gson().toJson(user)
    }
}

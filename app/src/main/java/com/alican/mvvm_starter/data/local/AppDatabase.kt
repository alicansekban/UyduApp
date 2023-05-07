package com.alican.mvvm_starter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.alican.mvvm_starter.data.local.model.PositionsItem
import com.alican.mvvm_starter.data.local.model.SatelliteDetailEntity
import com.alican.mvvm_starter.data.local.model.SatelliteModel
import com.alican.mvvm_starter.data.local.model.SatellitePositionsEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(
    entities = [SatelliteModel::class, SatelliteDetailEntity::class, SatellitePositionsEntity::class],
    version = 1
)
@TypeConverters(SatellitePositionsTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun satelliteDao(): SatelliteDao
    abstract fun satelliteDetailDao(): SatelliteDetailDao
    abstract fun satellitePositionsDao(): SatellitePositionsDao
}

class SatellitePositionsTypeConverter {
    @TypeConverter
    fun stringToPositionItem(value: String): List<PositionsItem> {
        val type = object : TypeToken<List<PositionsItem>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun positionItemToString(user: List<PositionsItem>): String {
        return Gson().toJson(user)
    }
}

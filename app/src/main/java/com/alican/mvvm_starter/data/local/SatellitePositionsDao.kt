package com.alican.mvvm_starter.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alican.mvvm_starter.data.local.model.SatellitePositionsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SatellitePositionsDao {

    @Query("Select * from satellite_positions where id = :id")
    fun getPositionsById(id : Int): Flow<SatellitePositionsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<SatellitePositionsEntity>)
}
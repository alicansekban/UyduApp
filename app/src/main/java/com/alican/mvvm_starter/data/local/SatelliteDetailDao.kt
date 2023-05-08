package com.alican.mvvm_starter.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alican.mvvm_starter.data.local.model.SatelliteDetailEntity

@Dao
interface SatelliteDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(model: List<SatelliteDetailEntity>)

    @Query("Select * From satellite_detail Where id = :id")
    fun getDetailById(id: Int) : LiveData<SatelliteDetailEntity>

    @Query("Select Count(*) from satellite_detail")
    suspend fun getSatelliteCount() : Int
}
package com.alican.mvvm_starter.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alican.mvvm_starter.data.local.model.SatelliteDetailEntity
import com.alican.mvvm_starter.data.local.model.SatelliteModel

@Dao
interface SatelliteDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(model: List<SatelliteDetailEntity>)

    @Query("SELECT * FROM satellite_detail")
    fun getAllSatellite() : LiveData<List<SatelliteDetailEntity>>

    @Query("Select * From satellite_detail Where id = :id")
    fun getDetailById(id: Int) : LiveData<SatelliteDetailEntity>

    @Delete
    fun deleteTable(entity: SatelliteDetailEntity)

}
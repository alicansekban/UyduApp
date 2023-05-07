package com.alican.mvvm_starter.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alican.mvvm_starter.data.local.model.SatelliteModel

@Dao
interface SatelliteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(dataModel: SatelliteModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSatelliteData(items : List<SatelliteModel>)


    @Query("SELECT * FROM satellite_list WHERE name LIKE '%' || :searchQuery || '%'" )
    fun getWithQuery(searchQuery: String) : LiveData<List<SatelliteModel>>

    @Query("SELECT * FROM satellite_list")
    fun getAllSatellite() : LiveData<List<SatelliteModel>>

    @Query("Select Count(*) from satellite_list")
    suspend fun getSatelliteCount() : Int

  //  @Query("SELECT FROM satellite_list WHERE id = :id")
   // fun getDetailById(id:Int)

    @Delete
    fun deleteTable(entity:SatelliteModel)
}

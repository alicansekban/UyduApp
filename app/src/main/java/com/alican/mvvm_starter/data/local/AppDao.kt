package com.alican.mvvm_starter.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alican.mvvm_starter.data.local.model.SatelliteModel

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(dataModel: SatelliteModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSatelliteData(items : List<SatelliteModel>)

    @Query("SELECT * FROM satellite_list")
    fun getAllSatellite() : LiveData<List<SatelliteModel>>

  //  @Query("SELECT FROM satellite_list WHERE id = :id")
   // fun getDetailById(id:Int)

    @Query("DELETE FROM satellite_list")
    fun deleteTable()
}

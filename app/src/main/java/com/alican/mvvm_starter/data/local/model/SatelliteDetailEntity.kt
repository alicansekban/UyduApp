package com.alican.mvvm_starter.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "satellite_detail")
@Parcelize
data class SatelliteDetailEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var cost_per_launch : Int = 0,
    var first_flight : String = "",
    var height : Int = 0,
    var mass : Int = 0,
): Parcelable

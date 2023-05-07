package com.alican.mvvm_starter.data.local.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "satellite_list")
data class SatelliteModel(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var active:Boolean,
    var name :String
):Parcelable

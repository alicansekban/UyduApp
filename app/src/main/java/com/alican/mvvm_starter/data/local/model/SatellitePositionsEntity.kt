package com.alican.mvvm_starter.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SatellitePositionModel(
    val list: List<SatellitePositionsEntity?>? = null
) : Parcelable

@Parcelize
data class PositionsItem(
    val posX: Double? = null,
    val posY: Double? = null
) : Parcelable

@Entity(tableName = "satellite_positions")
@Parcelize
data class SatellitePositionsEntity(
    val positions: List<PositionsItem>,
    @PrimaryKey(autoGenerate = true)
    val id: Int
) : Parcelable

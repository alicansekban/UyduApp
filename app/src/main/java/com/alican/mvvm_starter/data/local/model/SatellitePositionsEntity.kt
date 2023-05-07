package com.alican.mvvm_starter.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
@Entity(tableName = "satellite_positions")
@Parcelize
data class SatellitePositionsEntity(
	@PrimaryKey(autoGenerate = true)
	val list: List<ListItem?>? = null
) : Parcelable

@Parcelize
data class PositionsItem(
	val posX: Double? = null,
	val posY: Double? = null
) : Parcelable

@Parcelize
data class ListItem(
	val positions: List<PositionsItem?>? = null,
	val id: String? = null
) : Parcelable

package com.alican.mvvm_starter.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alican.mvvm_starter.R
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "satellite_list")
data class SatelliteModel(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var active:Boolean,
    var name :String
):Parcelable {

    fun getActiveText() = if (active) "Active" else "Passive"
    fun getTextColor() = if (active) R.color.black else R.color.grey
    fun getActiveImage() = if(active) R.drawable.bg_oval_green else R.drawable.bg_oval_red
}

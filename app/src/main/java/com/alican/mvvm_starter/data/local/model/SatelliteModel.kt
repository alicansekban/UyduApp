package com.alican.mvvm_starter.data.local.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "satellite_list")
data class SatelliteModel(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var active:Boolean,
    var name :String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeByte(if (active) 1 else 0)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SatelliteModel> {
        override fun createFromParcel(parcel: Parcel): SatelliteModel {
            return SatelliteModel(parcel)
        }

        override fun newArray(size: Int): Array<SatelliteModel?> {
            return arrayOfNulls(size)
        }
    }
}

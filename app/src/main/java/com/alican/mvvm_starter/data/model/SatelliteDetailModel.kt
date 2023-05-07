package com.alican.mvvm_starter.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity

class SatelliteDetailModel() : Parcelable{
    var id : Int = 0
    var cost_per_launch : Int = 0
    var first_flight : String = ""
    var height : Int = 0
    var mass : Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        cost_per_launch = parcel.readInt()
        first_flight = parcel.readString().toString()
        height = parcel.readInt()
        mass = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(cost_per_launch)
        parcel.writeString(first_flight)
        parcel.writeInt(height)
        parcel.writeInt(mass)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SatelliteDetailModel> {
        override fun createFromParcel(parcel: Parcel): SatelliteDetailModel {
            return SatelliteDetailModel(parcel)
        }

        override fun newArray(size: Int): Array<SatelliteDetailModel?> {
            return arrayOfNulls(size)
        }
    }
}


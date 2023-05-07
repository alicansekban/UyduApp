package com.alican.mvvm_starter.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import java.io.Serializable
@Entity(tableName = "")
class SatelliteListItem() : Parcelable {
    var id: Int = 0
    var active: Boolean = false
    var name: String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        active = parcel.readByte() != 0.toByte()
        name = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeByte(if (active) 1 else 0)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SatelliteListItem> {
        override fun createFromParcel(parcel: Parcel): SatelliteListItem {
            return SatelliteListItem(parcel)
        }

        override fun newArray(size: Int): Array<SatelliteListItem?> {
            return arrayOfNulls(size)
        }
    }
}



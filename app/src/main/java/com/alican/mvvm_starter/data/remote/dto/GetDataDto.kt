package com.alican.mvvm_starter.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.alican.mvvm_starter.data.local.model.SatelliteModel

data class GetDataDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("active")
    val active: Boolean
)

fun GetDataDto.toDataModel(): SatelliteModel {
    return SatelliteModel(name = this.name,
    id = this.id,
    active = this.active)
}

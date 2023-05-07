package com.alican.mvvm_starter.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SatelliteListItem(
    var id: Int = 0,
    var active: Boolean = false,
    var name: String = ""
)



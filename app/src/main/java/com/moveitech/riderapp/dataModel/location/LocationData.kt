package com.moveitech.riderapp.dataModel.location

data class LocationData(
    val BusinessId: String,
    val CurrentDateTime: String,
    val Latitude: String,
    val Longitude: String,
    val NoOfAttempt: Int,
    val TracingType: Int,
    val TrackingCode: String
)
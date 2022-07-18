package com.moveitech.riderapp.dataModel.location

data class TrackingResponse(
    val Code: Int,
    val Data: ArrayList<TrackingData>,
    val Message: String
)
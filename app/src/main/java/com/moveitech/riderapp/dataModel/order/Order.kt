package com.moveitech.riderapp.dataModel.order

import java.io.Serializable

data class Order(
    val DeliveryDateTime: String="",
    val Distance: String="",
    val Latitude: String="",
    val Longitude: String="",
    val OrderNo: String="",
    val PartyAddress: String="",
    val PartyEmail: String="",
    val PartyMobile: String="",
    val PartyName: String="",
    var Status: String="",
    val TotalAmount: Double=0.0,
    val TracingCode: String="",
    val TracingCodeBusinessWise: String="",
    var btnAction:Int=0,
):Serializable
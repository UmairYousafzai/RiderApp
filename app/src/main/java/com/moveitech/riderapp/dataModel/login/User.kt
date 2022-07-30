package com.moveitech.riderapp.dataModel.login

import java.io.Serializable

data class User(
    val Address: String="",
    val Cnic: String="",
    val DOB: String="",
    val DOJ: String="",
    val DOL: String="",
    val Details: String="",
    val Email: String="",
    val Mobile: String="",
    val Password: String="",
    val Phone: String="",
    val RiderCode: String="",
    val RiderName: String="",
    val RoleId: String="",
    val RoleName: String="",
    val ShiftEndTime: String="",
    val ShiftStartTime: String="",
    val Status: String=""
):Serializable
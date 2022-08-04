package com.moveitech.riderapp.dataModel.riderRole

import java.io.Serializable

data class RiderRole(
    val BusinessId: String="0000000001",
    var IsActive: Boolean=true,
    var OperationMode: String="INSERT",
    val RoleId: String="",
    var RoleName: String=""
):Serializable
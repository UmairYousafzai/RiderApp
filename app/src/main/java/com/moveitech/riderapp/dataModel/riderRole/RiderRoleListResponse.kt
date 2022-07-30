package com.moveitech.riderapp.dataModel.riderRole

import com.moveitech.riderapp.dataModel.generalReponse.BaseResponse
import java.io.Serializable

data class RiderRoleListResponse(
    val Data:ArrayList<RiderRole>
):BaseResponse(),Serializable

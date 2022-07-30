package com.moveitech.riderapp.dataModel.rider

import com.moveitech.riderapp.dataModel.generalReponse.BaseResponse
import java.io.Serializable

data class RiderListResponse(
    val Data:ArrayList<Rider>
):BaseResponse(),Serializable

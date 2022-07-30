package com.moveitech.riderapp.dataModel.rider

import com.moveitech.riderapp.dataModel.generalReponse.BaseResponse
import java.io.Serializable

data class SaveRiderResponse(
    val Data:Rider
):BaseResponse(),Serializable

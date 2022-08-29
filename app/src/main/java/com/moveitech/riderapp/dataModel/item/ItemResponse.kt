package com.moveitech.riderapp.dataModel.item

import com.moveitech.riderapp.dataModel.generalReponse.BaseResponse

data class ItemResponse(
    val Data: ArrayList<Item>,
):BaseResponse()
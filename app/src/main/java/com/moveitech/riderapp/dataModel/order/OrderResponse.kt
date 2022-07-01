package com.moveitech.riderapp.dataModel.order

import com.moveitech.riderapp.dataModel.generalReponse.BaseResponse
import java.io.Serializable

data class OrderResponse(
    val Data: List<Order>
):BaseResponse(),Serializable
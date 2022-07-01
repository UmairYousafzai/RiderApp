package com.moveitech.riderapp.dataModel.login

import com.moveitech.riderapp.dataModel.generalReponse.BaseResponse
import java.io.Serializable

data class LoginResponse(
    val Data: User
): BaseResponse(),Serializable
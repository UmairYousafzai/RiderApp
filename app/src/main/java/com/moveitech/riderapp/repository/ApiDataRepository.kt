package com.moveitech.riderapp.repository


import com.moveitech.riderapp.dataModel.generalReponse.BaseResponse
import com.moveitech.riderapp.dataModel.location.LocationData
import com.moveitech.riderapp.dataModel.location.LocationRequest
import com.moveitech.riderapp.dataModel.location.TrackingResponse
import com.moveitech.riderapp.dataModel.login.LoginResponse
import com.moveitech.riderapp.dataModel.order.OrderResponse
import com.moveitech.riderapp.network.ResultWrapper
import com.moveitech.riderapp.network.RetrofitClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.http.Query
import javax.inject.Inject

class ApiDataRepository @Inject constructor() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun login(
        mobileNum: String,
        password: String,
        date: String,
        businessID: String
    ): ResultWrapper<LoginResponse> {
        return safeApiCall(dispatcher) {
            RetrofitClient.getApi().login(mobileNum, password, date, businessID)
        }
    }

    suspend fun getOrders(riderCode: String, businessID: String): ResultWrapper<OrderResponse> {
        return safeApiCall(dispatcher) { RetrofitClient.getApi().getOrders(riderCode, businessID) }
    }

    suspend fun saveLocation(locationData: LocationRequest): ResultWrapper<BaseResponse> {
        return safeApiCall(dispatcher) { RetrofitClient.getApi().saveLocation(locationData) }
    }

    suspend fun saveStatus(
        trackingID: String,
        status: Int,
        businessID: String
    ): ResultWrapper<BaseResponse> {
        return safeApiCall(dispatcher) { RetrofitClient.getApi().saveOrderStatus(trackingID,status,businessID) }
    }


    suspend fun getTrackingData(trackingID: String,id:String, businessID: String): ResultWrapper<TrackingResponse> {
        return safeApiCall(dispatcher) { RetrofitClient.getApi().getTrackingData(trackingID,id, businessID) }
    }
}
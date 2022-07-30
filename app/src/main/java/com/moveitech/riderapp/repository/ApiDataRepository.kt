package com.moveitech.riderapp.repository


import com.moveitech.riderapp.dataModel.generalReponse.BaseResponse
import com.moveitech.riderapp.dataModel.location.LocationRequest
import com.moveitech.riderapp.dataModel.location.TrackingResponse
import com.moveitech.riderapp.dataModel.login.LoginResponse
import com.moveitech.riderapp.dataModel.order.OrderResponse
import com.moveitech.riderapp.dataModel.rider.Rider
import com.moveitech.riderapp.dataModel.rider.RiderListResponse
import com.moveitech.riderapp.dataModel.rider.SaveRiderResponse
import com.moveitech.riderapp.dataModel.riderRole.RiderRole
import com.moveitech.riderapp.network.ResultWrapper
import com.moveitech.riderapp.network.RetrofitClient
import com.moveitech.riderapp.dataModel.riderRole.RiderRoleListResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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


    suspend fun getRiders( businessID: String): ResultWrapper<RiderListResponse> {
        return safeApiCall(dispatcher) { RetrofitClient.getApi().getRiders( businessID) }
    }

    suspend fun getRiderRoles( businessID: String): ResultWrapper<RiderRoleListResponse> {
        return safeApiCall(dispatcher) { RetrofitClient.getApi().getRiderRoles( businessID) }
    }

    suspend fun getRiderByCode( riderID: String): ResultWrapper<SaveRiderResponse> {
        return safeApiCall(dispatcher) { RetrofitClient.getApi().getRiderByCode( riderID) }
    }

    suspend fun saveRider( rider: Rider): ResultWrapper<SaveRiderResponse> {
        return safeApiCall(dispatcher) { RetrofitClient.getApi().saveRider( rider) }
    }
    suspend fun saveRole( role: RiderRole): ResultWrapper<BaseResponse> {
        return safeApiCall(dispatcher) { RetrofitClient.getApi().saveRole( role) }
    }
}
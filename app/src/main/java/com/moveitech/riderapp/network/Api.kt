package com.moveitech.riderapp.network

import com.moveitech.riderapp.dataModel.generalReponse.BaseResponse
import com.moveitech.riderapp.dataModel.item.ItemResponse
import com.moveitech.riderapp.dataModel.location.LocationRequest
import com.moveitech.riderapp.dataModel.location.TrackingResponse
import com.moveitech.riderapp.dataModel.login.LoginResponse
import com.moveitech.riderapp.dataModel.order.Document
import com.moveitech.riderapp.dataModel.order.OrderResponse
import com.moveitech.riderapp.dataModel.party.PartyResponse
import com.moveitech.riderapp.dataModel.rider.Rider
import com.moveitech.riderapp.dataModel.rider.RiderListResponse
import com.moveitech.riderapp.dataModel.rider.SaveRiderResponse
import com.moveitech.riderapp.dataModel.riderRole.RiderRole
import com.moveitech.riderapp.dataModel.riderRole.RiderRoleListResponse
import com.moveitech.riderapp.utils.Constants.Companion.LOGIN
import com.moveitech.riderapp.utils.Constants.Companion.RIDERS
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_BY_CODE
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_ROLE
import com.moveitech.riderapp.utils.Constants.Companion.SALE_ORDER_BY_RIDER
import com.moveitech.riderapp.utils.Constants.Companion.SAVE_ORDER
import com.moveitech.riderapp.utils.Constants.Companion.SAVE_ORDER_STATUS
import com.moveitech.riderapp.utils.Constants.Companion.SAVE_ORDER_TRACKING
import com.moveitech.riderapp.utils.Constants.Companion.SAVE_RIDERS
import com.moveitech.riderapp.utils.Constants.Companion.SAVE_RIDER_ROLE
import com.moveitech.riderapp.utils.Constants.Companion.TRACKING_BY_SALE_ORDER
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface Api {

    /****************************   Authentication **********************/

    @GET(LOGIN)
    suspend fun login(
        @Query("Mobile") mobile: String,
        @Query("password") password: String,
        @Query("SystemDate") date: String,
        @Query("BusinessId") businessID: String
    ): LoginResponse


    /**************************** Tracking **********************/

    @GET(TRACKING_BY_SALE_ORDER)
    suspend fun getTrackingData(
        @Query("TrackingId") trackingID: String,
        @Query("Id") id: String,
        @Query("BusinessId") businessID: String
    ): TrackingResponse

    /****************************   Order **********************/

    @GET(SALE_ORDER_BY_RIDER)
    suspend fun getOrders(
        @Query("RiderCode") riderCode: String,
        @Query("BusinessId") businessID: String
    ): OrderResponse


    @POST(SAVE_ORDER_TRACKING)
    suspend fun saveLocation(@Body locationRequest: LocationRequest): BaseResponse

    @POST(SAVE_ORDER_STATUS)
    suspend fun saveOrderStatus(
        @Query("TrackingId") trackingID: String,
        @Query("Status") status: Int,
        @Query("BusinessId") businessID: String
    ): BaseResponse

    /****************************   Item **********************/


    @GET("api/Product/ProductData")
    suspend fun getProducts(@Query("BusinessId") businessID: String): ItemResponse


    /****************************   Rider **********************/

    @GET(RIDERS)
    suspend fun getRiders(
        @Query("businessId") businessID: String
    ): RiderListResponse

    @GET(RIDER_ROLE)
    suspend fun getRiderRoles(
        @Query("businessId") businessID: String
    ): RiderRoleListResponse

    @GET(RIDER_BY_CODE)
    suspend fun getRiderByCode(
        @Query("Code") riderCode: String
    ): SaveRiderResponse

    @POST(SAVE_RIDERS)
    suspend fun saveRider(@Body rider: Rider): SaveRiderResponse

    @POST(SAVE_RIDER_ROLE)
    suspend fun saveRole(@Body role: RiderRole): BaseResponse
    @POST(SAVE_ORDER)
    suspend fun saveOrder(@Body document: Document): BaseResponse

    /****************************   Party **********************/

    @GET("api/Party/PartyData")
    suspend fun getParties(
        @Query("BusinessId") businessID: String?,
        @Query("partytype") partyType: String?
    ): PartyResponse

}
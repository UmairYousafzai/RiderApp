package com.moveitech.riderapp.viewModel


import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moveitech.riderapp.adapter.OrdersAdapter
import com.moveitech.riderapp.dataModel.generalReponse.BaseResponse
import com.moveitech.riderapp.dataModel.location.TrackingData
import com.moveitech.riderapp.dataModel.location.TrackingResponse
import com.moveitech.riderapp.dataModel.login.LoginResponse
import com.moveitech.riderapp.dataModel.login.User
import com.moveitech.riderapp.dataModel.order.Order
import com.moveitech.riderapp.dataModel.order.OrderResponse
import com.moveitech.riderapp.network.ResultWrapper
import com.moveitech.riderapp.repository.ApiDataRepository
import com.moveitech.riderapp.utils.OneShotEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class OrderViewModel @Inject constructor(private val dataRepository: ApiDataRepository) :
    BaseViewModel() {

    val adapter: OrdersAdapter = OrdersAdapter(viewModel = this)

    val orderResponse: MutableLiveData<OneShotEvent<OrderResponse>> = MutableLiveData()
    val btnAction: MutableLiveData<OneShotEvent<Order>> = MutableLiveData()
    val orderStatusResponse: MutableLiveData<OneShotEvent<BaseResponse>> = MutableLiveData()
    val trackingResponse: MutableLiveData<OneShotEvent<ArrayList<TrackingData>>> = MutableLiveData()

    var order: Order = Order()


//    fun onClick(key: Int = 0) {
//
//        btnAction.value = Order(btnAction = key)
//    }

    fun onAdapterItemClick(key: Int = 0, model: Order) {

        order = model
        order.btnAction = key
        btnAction.value = OneShotEvent(model)
    }


    fun getOrders(riderCode: String, showProgress: Boolean) {
        viewModelScope.launch {
            showProgressBar(showProgress)
            dataRepository.getOrders(riderCode, "0000000001")
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            orderResponse.value = OneShotEvent(response.value)
                            adapter.setList(response.value.Data)
                        }
                        else -> handleErrorType(response)
                    }
                }
        }
    }

    fun getTrackingData(trackingID: String, showProgress: Boolean) {
        viewModelScope.launch {
            showProgressBar(showProgress)
            dataRepository.getTrackingData(trackingID,"0", "0000000001")
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            trackingResponse.value = OneShotEvent(response.value.Data)
                        }
                        else -> handleErrorType(response)
                    }
                }
        }
    }

    fun saveOrders(
        trackingID: String,
        status: Int,
        showProgress: Boolean
    ) {
        viewModelScope.launch {
            showProgressBar(showProgress)
            dataRepository.saveStatus(trackingID,status, "0000000001")
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            orderStatusResponse.value= OneShotEvent(response.value)
                        }
                        else -> handleErrorType(response)
                    }
                }
        }
    }


}
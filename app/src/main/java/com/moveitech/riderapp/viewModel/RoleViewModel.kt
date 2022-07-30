package com.moveitech.riderapp.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moveitech.riderapp.adapter.OrdersAdapter
import com.moveitech.riderapp.adapter.RoleAdapter
import com.moveitech.riderapp.dataModel.location.TrackingData
import com.moveitech.riderapp.dataModel.order.Order
import com.moveitech.riderapp.dataModel.riderRole.RiderRole
import com.moveitech.riderapp.network.ResultWrapper
import com.moveitech.riderapp.repository.ApiDataRepository
import com.moveitech.riderapp.dataModel.riderRole.RiderRoleListResponse
import com.moveitech.riderapp.utils.Constants.Companion.SAVE_ROLE_BTN
import com.moveitech.riderapp.utils.OneShotEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class RoleViewModel @Inject constructor(private val dataRepository: ApiDataRepository) :
    BaseViewModel() {

    val adapter: RoleAdapter = RoleAdapter(viewModel = this)

    val roleResponse: MutableLiveData<OneShotEvent<RiderRoleListResponse>> = MutableLiveData()
    val btnAction: MutableLiveData<Int> = MutableLiveData()

    val role: MutableLiveData<RiderRole> = MutableLiveData(RiderRole())


    fun onClick(key: Int = 0) {

        btnAction.value = key
        if (SAVE_ROLE_BTN == key) {
            if (validateData()) {
                saveRole(true)
            } else {
                showDialogMessage("Please enter the role name")
            }
        }
    }

    private fun validateData(): Boolean {

        if (role.value?.RoleName?.isEmpty() == true) {
            return false
        }
        return true
    }

    fun onAdapterItemClick(key: Int = 0, model: RiderRole) {

        role.value = model
    }


    fun getRoles(showProgress: Boolean) {
        viewModelScope.launch {
            showProgressBar(showProgress)
            dataRepository.getRiderRoles("0000000001")
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            roleResponse.value = OneShotEvent(response.value)
                            adapter.setList(response.value.Data)
                        }
                        else -> handleErrorType(response)
                    }
                }
        }
    }


    //
    private fun saveRole(
        showProgress: Boolean
    ) {
        viewModelScope.launch {
            showProgressBar(showProgress)
            role.value?.let {
                dataRepository.saveRole(it)
                    .let { response ->
                        showProgressBar(false)

                        when (response) {
                            is ResultWrapper.Success -> {
                                showDialogMessage(response.value.Message)
                            }
                            else -> handleErrorType(response)
                        }
                    }
            }
        }
    }


}
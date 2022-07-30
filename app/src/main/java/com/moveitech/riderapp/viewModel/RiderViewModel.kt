package com.moveitech. riderapp.viewModel


import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moveitech.riderapp.adapter.RiderAdapter
import com.moveitech.riderapp.dataModel.rider.Rider
import com.moveitech.riderapp.dataModel.rider.RiderListResponse
import com.moveitech.riderapp.network.ResultWrapper
import com.moveitech.riderapp.repository.ApiDataRepository
import com.moveitech.riderapp.dataModel.riderRole.RiderRole
import com.moveitech.riderapp.utils.Constants.Companion.SAVE_RIDER_BTN
import com.moveitech.riderapp.utils.OneShotEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RiderViewModel @Inject constructor(private val dataRepository: ApiDataRepository) :
    BaseViewModel() {

    val adapter: RiderAdapter = RiderAdapter(viewModel = this)
    val riderListResponse: MutableLiveData<OneShotEvent<RiderListResponse>> = MutableLiveData()
    val riderRoles: MutableLiveData<OneShotEvent<ArrayList<RiderRole>>> = MutableLiveData()
    val btnAction: MutableLiveData<OneShotEvent<Int>> = MutableLiveData()
    val rider: MutableLiveData<Rider> = MutableLiveData()
    val riderObserver: MutableLiveData<Rider> = MutableLiveData(Rider())
    private val riderRoleIdMap: MutableMap<String,Int> = mutableMapOf()
    private val riderRoleNameMap: MutableMap<Int,String> = mutableMapOf()

    val roleName: ObservableField<String> = ObservableField("")
//    get() {
//        rider.value?.RoleId= riderRoleIdMap[roleName.get()]!!
//        return field
//    }

    init {
        getRiderRoles(true)
    }

    fun onClick(key: Int = 0) {
        btnAction.value = OneShotEvent(key)

        if (SAVE_RIDER_BTN == key) {
            riderObserver.value?.RoleId = riderRoleIdMap[roleName.get()]!!
            if (validateRiderData()) {
                riderObserver.value?.let { saveRider(it, true) }
            } else
                showDialogMessage("Name,Address,Mobile and Role are mandatory fields")
        }
    }

    private fun validateRiderData(): Boolean {
        if (riderObserver.value?.RiderName.isNullOrEmpty() ||
            riderObserver.value?.Mobile.isNullOrEmpty() ||
            (riderObserver.value?.RoleId ?: 0) == 0 ||
            riderObserver.value?.Address.isNullOrEmpty()
        ) {
            return false
        }

        return true
    }

    fun onAdapterItemClick(key: Int = 0, model: Rider) {

        rider.value = model
    }


    fun getRiders(showProgress: Boolean) {
        viewModelScope.launch {

            showProgressBar(showProgress)
            dataRepository.getRiders("0000000001")
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            riderListResponse.value = OneShotEvent(response.value)
                            adapter.setList(response.value.Data)
                        }
                        else -> handleErrorType(response)
                    }
                }
        }
    }

    private fun getRiderRoles(showProgress: Boolean) {
        viewModelScope.launch {
            showProgressBar(showProgress)
            dataRepository.getRiderRoles("0000000001")
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            riderRoles.value= OneShotEvent(response.value.Data)
                            mapValueOfRolesID(response.value.Data)
                        }
                        else -> handleErrorType(response)
                    }
                }
        }
    }

    private fun mapValueOfRolesID(value: ArrayList<RiderRole>) {

        for (model in value )
        {
            if (model.RoleId.isEmpty().not()){
                riderRoleIdMap[model.RoleName] = model.RoleId.toInt()
                riderRoleNameMap[model.RoleId.toInt()] = model.RoleName

            }
        }
        roleName.set(rider.value?.let { riderRoleNameMap[it.RoleId] })
    }


    fun getRiderByCode(riderID:String,showProgress: Boolean) {
        viewModelScope.launch {
            showProgressBar(showProgress)
            dataRepository.getRiderByCode(riderID)
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {

                            rider.value = response.value.Data
                            rider.value?.Action="UPDATE"
                            riderObserver.value=(rider.value!!)

                            roleName.set(riderRoleNameMap[rider.value?.RoleId])
                        }
                        else -> handleErrorType(response)
                    }
                }
        }
    }


    private fun saveRider(
        rider: Rider,
        showProgress: Boolean
    ) {
        viewModelScope.launch {
            showProgressBar(showProgress)
            dataRepository.saveRider(rider)
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
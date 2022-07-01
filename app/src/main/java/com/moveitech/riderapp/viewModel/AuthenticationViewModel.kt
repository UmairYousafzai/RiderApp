package com.moveitech.riderapp.viewModel


import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moveitech.riderapp.dataModel.login.LoginResponse
import com.moveitech.riderapp.network.ResultWrapper
import com.moveitech.riderapp.repository.ApiDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(val dataRepository: ApiDataRepository): BaseViewModel() {


    val mobileNum: ObservableField<String> = ObservableField("")
    val passWord: ObservableField<String> = ObservableField("")
    val loginResponse: MutableLiveData<LoginResponse?> = MutableLiveData()
    val mobileNumberError: MutableLiveData<Boolean> = MutableLiveData()
    val passwordError: MutableLiveData<Boolean> = MutableLiveData()



    fun onClick(key: Int=0) {

        if (validateFields()) {
            mobileNumberError.value = false
            passwordError.value = false
            login()
        } else {
            mobileNumberError.value = true
            passwordError.value = true
        }
    }

    private fun validateFields(): Boolean {
        var flag = true
        if (mobileNum.get()?.length ?: 0 == 0&& mobileNum.get()?.length ?:0 !=11) {
            flag = false
        }

        if (passWord.get()?.length ?: 0 == 0) {
            flag = false
        }

        return flag
    }

    private fun login() {
        viewModelScope.launch {
            showProgressBar(true)
            dataRepository.login(mobileNum.get().toString(), passWord.get().toString(),getDate(),"0000000001")
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            if (response.value.Code == 200) {
                                loginResponse.value = response.value
                            } else {
                                mobileNumberError.value = true
                                passwordError.value = true
                            }
                        }
                        else -> handleErrorType(response)
                    }
                }
        }
    }

    private fun getDate(): String {
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm aaa")
        return dateFormat.format(date)
    }
}
package com.moveitech.riderapp.ui.authentication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moveitech.riderapp.dataModel.login.User
import com.moveitech.riderapp.viewModel.AuthenticationViewModel
import com.moveitech.riderapp.databinding.FragmentLoginBinding
import com.moveitech.riderapp.ui.BaseFragment
import com.moveitech.riderapp.utils.DataStoreHelper
import com.moveitech.riderapp.utils.hideToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var dataStoreHelper: DataStoreHelper
    private val viewModel: AuthenticationViewModel by viewModels()

    override fun initViews() {

        hideToolbar()
        binding.viewModel=viewModel
        showProgressDialog(false)
    }



    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(layoutInflater, container, false)


    override fun liveDataObserver() {
        with(viewModel)
        {
            setupGeneralViewModel(this)

            mobileNumberError.observe(viewLifecycleOwner)
            {
                setErrorOnFields(it)
            }

            loginResponse.observe(viewLifecycleOwner){
                it?.let { it1 -> saveLoginStatus(it1.Data) }
//                    moveToNextScreen(LoginFragmentDirections.actionFragmentToSecondGraph())


            }

        }

    }

    private fun saveLoginStatus(data: User) {

        lifecycleScope.launch {
            dataStoreHelper.saveIsLogin(true)
            dataStoreHelper.saveUser(data)
            moveToNextScreen(LoginFragmentDirections.actionLoginFragmentToOrderListFragment())
        }
    }


    override fun btnListener() {

    }

    private fun setErrorOnFields(flag:Boolean) {

        if (flag) {
            binding.etMobileLayout.error = "Enter Valid Mobile Number"
            binding.etPasswordLayout.error = "Enter Valid Password"
        } else {
            binding.etMobileLayout.error = null
            binding.etPasswordLayout.error = null
        }
    }


}
package com.moveitech.riderapp.ui

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.moveitech.riderapp.BuildConfig
import com.moveitech.riderapp.databinding.FragmentSplashBinding
import com.moveitech.riderapp.utils.DataStoreHelper
import com.moveitech.riderapp.utils.hideToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    @Inject
    lateinit var dataStore: DataStoreHelper

    override fun initViews() {

        binding.tvVersionName.text = "Version ${BuildConfig.VERSION_NAME}"
        hideToolbar()
        startSplash()

    }

    private fun startSplash() {
        lifecycleScope.launch {
            dataStore.isLogin.collect {
                if (it) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        moveToNextScreen(SplashFragmentDirections.actionSplashFragmentToOrderListFragment())
                    }, 1000)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        moveToNextScreen(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                    }, 1000)
                }
            }
        }
    }


    override fun liveDataObserver() {
    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSplashBinding.inflate(layoutInflater, container, false)


    override fun btnListener() {
    }


}
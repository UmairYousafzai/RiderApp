package com.moveitech.riderapp.ui.fragments.rider

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.moveitech.riderapp.databinding.FragmentRiderListBinding
import com.moveitech.riderapp.ui.fragments.BaseFragment
import com.moveitech.riderapp.utils.Constants.Companion.ADD_RIDER_BTN
import com.moveitech.riderapp.utils.DataStoreHelper
import com.moveitech.riderapp.utils.OneShotEvent
import com.moveitech.riderapp.utils.hideToolbar
import com.moveitech.riderapp.viewModel.RiderViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RiderListFragment : BaseFragment<FragmentRiderListBinding>() {

    private val TAG= RiderListFragment::class.java.simpleName

    @Inject
    lateinit var dataStore: DataStoreHelper
    private val viewModel: RiderViewModel by viewModels()

    override fun initViews() {
        Log.e(TAG,"===>")
        setHasOptionsMenu(true)
        hideToolbar()
        binding.viewModel = viewModel
        swipeListener()
    }



    private fun swipeListener() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.getRiders( false)
        }
    }



    override fun liveDataObserver() {

        with(viewModel)
        {
            setupGeneralViewModel(this)
            getRiders(true)
            riderListResponse.observe(viewLifecycleOwner)
            {
                it.getContentIfNotHandled()?.let { response ->
                    if (response.Data.size>0) {
                        binding.tvNoData.visibility= View.GONE
                    }else
                    {
                        binding.tvNoData.visibility= View.VISIBLE
                    }
                    binding.swipeLayout.isRefreshing = false
                }
            }

            btnAction.observe(viewLifecycleOwner)
            {
                it.getContentIfNotHandled()?.let {
                    if (it== ADD_RIDER_BTN){
                        moveToNextScreen(RiderListFragmentDirections.actionRiderListFragmentToAddRiderFragment())
                        btnAction.value= OneShotEvent(-10)
                    }

                }

            }
            rider.observe(viewLifecycleOwner){
                if (it!=null&& it.RiderCode.isEmpty().not())
                {
                    moveToNextScreen(RiderListFragmentDirections.actionRiderListFragmentToAddRiderFragment(it))
                    rider.value=null

                }
            }

        }
    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRiderListBinding.inflate(layoutInflater, container, false)


    override fun btnListener() {
    }


}
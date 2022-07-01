package com.moveitech.riderapp.ui

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moveitech.riderapp.R
import com.moveitech.riderapp.adapter.OrdersAdapter
import com.moveitech.riderapp.dataModel.order.Order
import com.moveitech.riderapp.databinding.FragmentOrderListBinding
import com.moveitech.riderapp.services.LocationService
import com.moveitech.riderapp.utils.*
import com.moveitech.riderapp.utils.Constants.Companion.ACTION_START_LOCATION_SERVICE
import com.moveitech.riderapp.utils.Constants.Companion.ACTION_STOP_LOCATION_SERVICE
import com.moveitech.riderapp.utils.Constants.Companion.EXTRA_LOCATION_TYPE
import com.moveitech.riderapp.utils.Constants.Companion.EXTRA_TRACKING_ID
import com.moveitech.riderapp.utils.Constants.Companion.LOCATION_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_CANCEL_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_CLOSE_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_COMPlETE_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_DISPATCH_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_HOLD_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_PENDING_BTN
import com.moveitech.riderapp.viewModel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class OrderListFragment : BaseFragment<FragmentOrderListBinding>() {

    @Inject
    lateinit var dataStore: DataStoreHelper
    private val viewModel: OrderViewModel by viewModels()
    var riderCode = ""

    override fun initViews() {
        setHasOptionsMenu(true)
        showToolbar()
        getRiderDetails()
        swipeListener()
        binding.viewModel = viewModel


        Handler(Looper.getMainLooper()).postDelayed({
            getOrderNum()

        }, 400)
    }

    private fun getOrderNum() {

        lifecycleScope.launch {
            dataStore.orderNum.collect {
                (binding.rvOrderList.adapter as OrdersAdapter).setOrderID(it)
            }
        }
    }

    private fun swipeListener() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.getOrders(riderCode, false)
        }

    }

    private fun getRiderDetails() {

        lifecycleScope.launch {
            dataStore.user.collect {


                if (it.RiderCode.isEmpty().not()) {
                    viewModel.getOrders(it.RiderCode, true)
                }
                riderCode = it.RiderCode

            }
        }
    }


    override fun liveDataObserver() {

        with(viewModel)
        {
            setupGeneralViewModel(this)
            orderResponse.observe(viewLifecycleOwner)
            {
                it.getContentIfNotHandled().let {
                    binding.swipeLayout.isRefreshing = false

                }
            }

            btnAction.observe(viewLifecycleOwner)
            {
                it.getContentIfNotHandled()?.let {
                    if (it.btnAction!=-11) {
                        val order= it
                        checkBtnAction(order)
//                        it.btnAction = -11
//                        btnAction.value = it
                    }
                }

            }
            orderStatusResponse.observe(viewLifecycleOwner){

                showSnackBar("Status Changed Successfully Updated")
            }
        }
    }


    private fun checkBtnAction(order: Order?) {
        if (order != null) {
            when (order.btnAction) {
                LOCATION_BTN -> moveToNextScreen(OrderListFragmentDirections.actionOrderListFragmentToMapFragment(order.Longitude,order.Latitude))
                RIDER_DISPATCH_BTN -> {
                    startService(order, 1)
                    viewModel.saveOrders(order.TracingCode, RIDER_DISPATCH_BTN, true)
                }
                RIDER_COMPlETE_BTN -> {
                    startService(order, 2)
                    viewModel.saveOrders(order.TracingCode, RIDER_COMPlETE_BTN, true)
                }
                RIDER_PENDING_BTN -> {
                    viewModel.saveOrders(order.TracingCode, RIDER_PENDING_BTN, true)
                }
                RIDER_HOLD_BTN -> {
                    viewModel.saveOrders(order.TracingCode, RIDER_HOLD_BTN, true)
                }
                RIDER_CANCEL_BTN -> {
                    viewModel.saveOrders(order.TracingCode, RIDER_CANCEL_BTN, true)
                }
                RIDER_CLOSE_BTN -> startService(order, RIDER_CLOSE_BTN)

            }
        }
    }

    private fun startService(order: Order?, locationType: Int) {
        if (checkPermission()) {
            if (requireContext().isLocationEnabled()) {
                order?.let { saveOrderNum(it.OrderNo) }
                Intent(requireContext(), LocationService::class.java).apply {
                    putExtra(EXTRA_LOCATION_TYPE, locationType)
                    putExtra(EXTRA_TRACKING_ID, order?.TracingCode)
                    action = if (locationType != 5) ACTION_START_LOCATION_SERVICE
                    else ACTION_STOP_LOCATION_SERVICE
                    requireActivity().startService(this)
                }
            } else {
                showSnackBar("Please Turn On Location")
            }
        } else {
            takePermission()
        }

    }

    private fun saveOrderNum(orderNo: String) {

        lifecycleScope.launch {
            dataStore.saveOrderNum(orderNo)
        }
    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOrderListBinding.inflate(layoutInflater, container, false)


    override fun btnListener() {
    }


    override fun onDestroy() {
        super.onDestroy()

        showProgressDialog(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId== R.id.action_logout)
        {
            lifecycleScope.launch {
                dataStore.clear()
                moveToNextScreen(OrderListFragmentDirections.actionOrderListFragmentToLoginFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
package com.moveitech.riderapp.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.button.MaterialButton
import com.moveitech.riderapp.R
import com.moveitech.riderapp.dataModel.order.Order
import com.moveitech.riderapp.dataModel.rider.Rider
import com.moveitech.riderapp.databinding.OrderCardBinding
import com.moveitech.riderapp.databinding.RiderCardBinding
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_CANCEL_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_CLOSE_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_COMPlETE_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_DISPATCH_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_HOLD_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_PENDING_BTN
import com.moveitech.riderapp.utils.getDistanceBetweenLocation
import com.moveitech.riderapp.viewModel.OrderViewModel
import com.moveitech.riderapp.viewModel.RiderViewModel


class OrderListAdapter(list: ArrayList<Rider> = ArrayList(), val viewModel: OrderViewModel) :
    BaseAdapter<RiderCardBinding>(list) {



    override fun bind(binding: RiderCardBinding, item: Any, position: Int) {
        val model = item as Rider
//        binding.viewModel = viewModel
        binding.model = model
    }



    override fun setList(list: List<Any>) {
        dataList = list
        notifyDataSetChanged()
    }



    override fun getBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = RiderCardBinding.inflate(layoutInflater, container, false)


}
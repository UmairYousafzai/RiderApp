package com.moveitech.riderapp.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import com.google.android.material.button.MaterialButton
import com.moveitech.riderapp.R
import com.moveitech.riderapp.dataModel.order.Order
import com.moveitech.riderapp.databinding.OrderCardBinding
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_CANCEL_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_CLOSE_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_COMPlETE_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_DISPATCH_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_HOLD_BTN
import com.moveitech.riderapp.utils.Constants.Companion.RIDER_PENDING_BTN
import com.moveitech.riderapp.viewModel.OrderViewModel


class OrdersAdapter(list: ArrayList<String> = ArrayList(), val viewModel: OrderViewModel) :
    BaseAdapter<OrderCardBinding>(list) {

    var orderNum: String = ""

    override fun bind(binding: OrderCardBinding, item: Any, position: Int) {
        val model = item as Order
        binding.viewModel = viewModel
        binding.model = model

        binding.btnRiderLocationSelection.isActivated = model.equals(orderNum).not()


        binding.btnRiderLocationSelection.setOnClickListener {
            binding.btnRiderLocationSelection.setIconResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
            showPopUpMenu(binding.btnRiderLocationSelection, model, binding.tvStatus)
        }


    }

    private fun showPopUpMenu(btn: MaterialButton, model: Order, tvStatus: TextView) {

        val dropDownMenu = PopupMenu(btn.context, btn)
        dropDownMenu.menuInflater.inflate(R.menu.drop_down_menu, dropDownMenu.menu)
        dropDownMenu.setOnMenuItemClickListener {
            btn.text = it.title
            tvStatus.text = it.title
            btn.setIconResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            sendData(it, model)

            true
        }
        dropDownMenu.show()
    }

    private fun sendData(menuItem: MenuItem, model: Order) {
        when (menuItem.itemId) {
            R.id.action_dispatch -> {
                model.Status = RIDER_DISPATCH_BTN.toString()
                viewModel.onAdapterItemClick(RIDER_DISPATCH_BTN, model)
            }
            R.id.action_cancel -> {
                model.Status = RIDER_CANCEL_BTN.toString()
                viewModel.onAdapterItemClick(RIDER_CANCEL_BTN, model)
            }
            R.id.action_close -> {
                model.Status= RIDER_CLOSE_BTN.toString()
                viewModel.onAdapterItemClick(RIDER_CLOSE_BTN, model)
            }
            R.id.action_hold -> {
                model.Status= RIDER_HOLD_BTN.toString()
                viewModel.onAdapterItemClick(RIDER_HOLD_BTN, model)
            }
            R.id.action_pending -> {
                model.Status= RIDER_PENDING_BTN.toString()
                viewModel.onAdapterItemClick(RIDER_PENDING_BTN, model)
            }
            R.id.action_complete -> {
                model.Status= RIDER_COMPlETE_BTN.toString()
                viewModel.onAdapterItemClick(RIDER_COMPlETE_BTN, model)
            }
        }

    }

    override fun setList(list: List<Any>) {
        dataList = list
        notifyDataSetChanged()
    }

    fun setOrderID(orderNum: String) {
        this.orderNum = orderNum
        notifyDataSetChanged()
    }

    override fun getBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = OrderCardBinding.inflate(layoutInflater, container, false)


}
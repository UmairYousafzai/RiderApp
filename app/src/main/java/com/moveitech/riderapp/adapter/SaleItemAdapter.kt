package com.moveitech.riderapp.adapter

import android.graphics.Color
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moveitech.riderapp.dataModel.item.Item
import com.moveitech.riderapp.databinding.SaleItemCardBinding
import com.moveitech.riderapp.viewModel.OrderViewModel


class SaleItemAdapter(val list: ArrayList<Item> = ArrayList(), val viewModel: OrderViewModel) :
    BaseAdapter<SaleItemCardBinding>(list) {
    private var beforeEditItem: Item = Item()


    override fun bind(binding: SaleItemCardBinding, item: Any, position: Int) {
        val model = item as Item
        binding.viewModel = viewModel
        binding.item = model
        binding.btnEdit.setOnClickListener {
            editFunctionality(binding,position)
        }
        binding.btnDone.setOnClickListener {
            doneBtnFunctionality(binding,position)
        }

    }

    private fun doneBtnFunctionality(binding: SaleItemCardBinding, position: Int) {


        binding.etCost.inputType = InputType.TYPE_NULL
//                    mBinding.freeQty.setInputType(InputType.TYPE_NULL);
        //                    mBinding.freeQty.setInputType(InputType.TYPE_NULL);
        binding.qty.inputType = InputType.TYPE_NULL
        binding.etCost.isFocusableInTouchMode = false
        binding.qty.isFocusableInTouchMode = false
//                    mBinding.freeQty.setFocusableInTouchMode(false);
        //                    mBinding.freeQty.setFocusableInTouchMode(false);
        binding.itemCardTwoCard.setCardBackgroundColor(Color.WHITE)
        binding.btnEdit.visibility = View.VISIBLE
        binding.btnDone.visibility = View.GONE
        binding.item = list[position]
        editCalculation(beforeEditItem)
            viewModel.addItemToProductAdapter(list[position])
            viewModel.dialogMessage.value="Item Edited Successfully"

    }

    private fun editFunctionality(binding: SaleItemCardBinding,position:Int) {

        beforeEditItem= Item()
        beforeEditItem.UnitRetail= list[position].UnitRetail
        beforeEditItem.Qty= list[position].Qty
        binding.etCost.inputType = InputType.TYPE_CLASS_NUMBER
//                    mBinding.freeQty.setInputType(InputType.TYPE_CLASS_NUMBER);
        //                    mBinding.freeQty.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.qty.inputType = InputType.TYPE_CLASS_NUMBER
        binding.etCost.isFocusableInTouchMode = true
        binding.qty.isFocusableInTouchMode = true
//                    mBinding.freeQty.setFocusableInTouchMode(true);
        //                    mBinding.freeQty.setFocusableInTouchMode(true);
        binding.itemCardTwoCard.setCardBackgroundColor(Color.parseColor("#F2F2F2"))
        binding.btnDone.visibility = View.VISIBLE
        binding.btnEdit.visibility = View.GONE
        viewModel.dialogMessage.value="Edit Mode Enable"

    }

    override fun setList(list: List<Any>) {
        dataList = list
        notifyDataSetChanged()
    }

    override fun getBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = SaleItemCardBinding.inflate(layoutInflater, container, false)


    private fun disableUi(binding: SaleItemCardBinding) {
        binding.btnEdit.isEnabled = false
        binding.btnCancel.isEnabled = false
    }

    fun addItem(item: Item) {
        if (!list.contains(item)) {
            list.add(item)
            notifyDataSetChanged()
        }
    }

    fun removeItem(item: Item) {
        if (editCalculation(item)) {
            list.remove(item)
        }
        notifyDataSetChanged()
    }
    private fun editCalculation(item: Item): Boolean {
        val qty: Double = (viewModel.totalQTY.get()?.toDouble() ?:0.0 ) - item.Qty
        val amount: Double = (viewModel.subTotalAmount.get()?.toDouble() ?: 0.0) - item.Amount
            viewModel.subTotalAmount.set(amount.toString())
            viewModel.totalQTY.set(qty.toString())
        val flag: Boolean = try {
                    viewModel.totalAmount.set(amount.toString())
                true
            } catch (e: Exception) {
                Log.e(SaleItemAdapter::class.java.simpleName, e.toString())
                false
            }

        return flag
    }

    fun checkItemExists(item: Item): Boolean {
        return list.contains(item)
    }
}
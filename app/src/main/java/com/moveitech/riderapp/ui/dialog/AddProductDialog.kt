package com.moveitech.riderapp.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moveitech.riderapp.dataModel.item.Item
import com.moveitech.riderapp.databinding.CustomAddProductDialogBinding
import com.moveitech.riderapp.databinding.CustomAddRoleDialogBinding
import com.moveitech.riderapp.ui.fragments.order.AddOrderFragment
import com.moveitech.riderapp.ui.fragments.rider.AddRiderFragmentArgs
import com.moveitech.riderapp.utils.Constants.Companion.CANCEL_BTN
import com.moveitech.riderapp.viewModel.OrderViewModel
import com.moveitech.riderapp.viewModel.RoleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductDialog : BaseDialogFragment<CustomAddProductDialogBinding>() {
    private val item by lazy { arguments?.let { AddProductDialogArgs.fromBundle(it).item } }

    override fun initViews() {

        binding.model = item
        binding.executePendingBindings()

        liveDataObserver()

    }

    private fun liveDataObserver() {

    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = CustomAddProductDialogBinding.inflate(layoutInflater, container, false)

    override fun btnListener() {

        binding.btnSave.setOnClickListener {
            dismiss()

            item?.let {
                findNavController().previousBackStackEntry?.savedStateHandle?.set("item", it)
            }
        }

        binding.btnCancel.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("item", null)
            dismiss()
        }
    }
}
package com.moveitech.riderapp.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moveitech.riderapp.dataModel.item.Item
import com.moveitech.riderapp.dataModel.party.Party
import com.moveitech.riderapp.databinding.CustomAddCustomerDialogBinding
import com.moveitech.riderapp.databinding.CustomAddProductDialogBinding
import com.moveitech.riderapp.databinding.CustomAddRoleDialogBinding
import com.moveitech.riderapp.ui.fragments.order.AddOrderFragment
import com.moveitech.riderapp.ui.fragments.rider.AddRiderFragmentArgs
import com.moveitech.riderapp.utils.Constants.Companion.CANCEL_BTN
import com.moveitech.riderapp.viewModel.OrderViewModel
import com.moveitech.riderapp.viewModel.RoleViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddCustomerDialog : BaseDialogFragment<CustomAddCustomerDialogBinding>() {
    private val party: Party = Party()

    override fun initViews() {

        binding.model = party
        binding.executePendingBindings()

        liveDataObserver()

    }

    private fun liveDataObserver() {

    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = CustomAddCustomerDialogBinding.inflate(layoutInflater, container, false)

    override fun btnListener() {

        binding.btnSave.setOnClickListener {
            party.let {
                it.PartyCode= UUID.randomUUID().toString()
                findNavController().previousBackStackEntry?.savedStateHandle?.set("party", it)
            }
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}
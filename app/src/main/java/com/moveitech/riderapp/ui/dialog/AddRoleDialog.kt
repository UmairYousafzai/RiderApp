package com.moveitech.riderapp.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.moveitech.riderapp.databinding.CustomAddRoleDialogBinding
import com.moveitech.riderapp.ui.fragments.rider.AddRiderFragmentArgs
import com.moveitech.riderapp.utils.Constants.Companion.ADD_ROLE_BTN
import com.moveitech.riderapp.utils.Constants.Companion.CANCEL_BTN
import com.moveitech.riderapp.viewModel.RoleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRoleDialog: BaseDialogFragment<CustomAddRoleDialogBinding>() {
    private val viewModel: RoleViewModel by viewModels()
    private val role by lazy { arguments?.let { AddRoleDialogArgs.fromBundle(it).role } }


    override fun initViews() {
        if (role!=null)
        {
            role!!.OperationMode= "UPDATE"
            viewModel.role.value=role
        }
        binding.viewModel=viewModel
        binding.executePendingBindings()

        liveDataObserver()

    }

    private fun liveDataObserver() {

        with(viewModel)
        {
            setupGeneralViewModel(this)
            btnAction.observe(viewLifecycleOwner)
            {
                if (CANCEL_BTN==it)
                {
                    dismiss()
                }
            }
        }
    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    )= CustomAddRoleDialogBinding.inflate(layoutInflater,container,false)

    override fun btnListener() {
    }
}
package com.moveitech.riderapp.ui.fragments.rider

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.moveitech.riderapp.dataModel.riderRole.RiderRole
import com.moveitech.riderapp.databinding.FragmentAddRiderBinding
import com.moveitech.riderapp.ui.fragments.BaseFragment
import com.moveitech.riderapp.utils.*
import com.moveitech.riderapp.viewModel.RiderViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import javax.inject.Inject


@AndroidEntryPoint
class AddRiderFragment : BaseFragment<FragmentAddRiderBinding>() {
    private val TAG = AddRiderFragment::class.java.simpleName

    @Inject
    lateinit var dataStore: DataStoreHelper
    private val viewModel: RiderViewModel by viewModels()
    private val rider by lazy { arguments?.let { AddRiderFragmentArgs.fromBundle(it).rider } }

    override fun initViews() {
        Log.e(TAG, "===>")
        showToolbar()
        setToolbarTitle("Add Rider")
        if (rider?.RiderCode.isNullOrEmpty()) {
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }

    }


    override fun liveDataObserver() {

        with(viewModel)
        {
            setupGeneralViewModel(this)

            btnAction.observe(viewLifecycleOwner)
            {
                it.getContentIfNotHandled()?.let {

                }
            }

            riderRoles.observe(viewLifecycleOwner) {
                setRiderRolesAdapter(it.getContentIfNotHandled())
                if (this@AddRiderFragment.rider != null) {
                    this@AddRiderFragment.rider?.RiderCode?.let { riderCode ->
                        viewModel.getRiderByCode(riderCode, true)
                    }
                }
            }
            riderObserver.observe(viewLifecycleOwner) {
                if (it.RiderCode.isNullOrEmpty().not()) {
                    binding.viewModel = viewModel
                    binding.executePendingBindings()
                }

            }

        }
    }

    private fun setRiderRolesAdapter(list: ArrayList<RiderRole>?) {

        val roleNameList = ArrayList<String>()
        if (list != null) {
            for (model in list) {
                roleNameList.add(model.RoleName)
            }
        }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            roleNameList
        )
        binding.riderRoleSpinner.setAdapter(adapter)

    }


    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddRiderBinding.inflate(layoutInflater, container, false)


    override fun btnListener() {
    }


}
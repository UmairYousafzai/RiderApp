package com.moveitech.riderapp.ui.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.moveitech.riderapp.databinding.FragmentRoleListBinding
import com.moveitech.riderapp.utils.*
import com.moveitech.riderapp.viewModel.RoleViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RoleListFragment : BaseFragment<FragmentRoleListBinding>() {

    private val TAG= RoleListFragment::class.java.simpleName

    private val viewModel: RoleViewModel by viewModels()

    override fun initViews() {
        Log.e(TAG,"===>")
        showToolbar()
        binding.viewModel = viewModel
        swipeListener()
    }



    private fun swipeListener() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.getRoles( false)
        }
    }



    override fun liveDataObserver() {

        with(viewModel)
        {
            setupGeneralViewModel(this)
            getRoles(true)
            roleResponse.observe(viewLifecycleOwner)
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
                moveToNextScreen(RoleListFragmentDirections.actionRoleListFragmentToAddRoleDialog(null))
            }

            role.observe(viewLifecycleOwner){
                if (it!=null && it.RoleId.isEmpty().not())
                moveToNextScreen(RoleListFragmentDirections.actionRoleListFragmentToAddRoleDialog(it))
            }


        }
    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRoleListBinding.inflate(layoutInflater, container, false)


    override fun btnListener() {
    }


}
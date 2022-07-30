package com.moveitech.riderapp.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.moveitech.riderapp.utils.DialogUtils
import com.moveitech.riderapp.utils.showSnackBar
import com.moveitech.riderapp.viewModel.BaseViewModel

abstract class BaseDialogFragment<T : ViewBinding> : DialogFragment() {
    protected lateinit var binding: T
    lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog = DialogUtils.getProgressDialog(requireContext())
        binding = getFragmentBinding(layoutInflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        btnListener()
    }


    abstract fun initViews()


    abstract fun getFragmentBinding(layoutInflater: LayoutInflater, container: ViewGroup?): T
    abstract fun btnListener()
    protected fun setupGeneralViewModel(generalViewModel: BaseViewModel) {
        with(generalViewModel) {
            dialogMessage.observe(viewLifecycleOwner) {
//               showAlertDialog(it)
                showSnackBar(it)
            }

            progressBar.observe(viewLifecycleOwner) {
                showProgressDialog(it)

            }
        }
    }


    protected fun showProgressDialog(show: Boolean) {

        if (show) {
            if (!dialog.isShowing)
                dialog.apply { show() }
        } else if (!show) {
            if (dialog.isShowing)
                dialog.dismiss()
        }
    }
}
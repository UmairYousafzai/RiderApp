package com.moveitech.riderapp.ui.fragments.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.moveitech.riderapp.dataModel.item.Item
import com.moveitech.riderapp.dataModel.party.Party
import com.moveitech.riderapp.databinding.FragmentAddOrderBinding
import com.moveitech.riderapp.ui.fragments.BaseFragment
import com.moveitech.riderapp.utils.*
import com.moveitech.riderapp.utils.Constants.Companion.ADD_CUSTOMER_BTN
import com.moveitech.riderapp.utils.Constants.Companion.ADD_PRODUCT_BTN
import com.moveitech.riderapp.viewModel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AddOrderFragment : BaseFragment<FragmentAddOrderBinding>() {

    @Inject
    lateinit var dataStore: DataStoreHelper
    private val viewModel: OrderViewModel by viewModels()
    var riderCode = ""

    override fun initViews() {
        setHasOptionsMenu(true)
        showToolbar()
        setToolbarTitle("Add Orders")

        binding.viewModel = viewModel

        getUserId()

    }

    private fun getUserId() {

       lifecycleScope.launch {
           dataStore.user.collect {
               viewModel.userID= it.RiderCode
           }
       }
    }


    override fun liveDataObserver() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Item>("item")
            ?.observe(
                viewLifecycleOwner
            ) {
                if (it != null) {
                    saveItemFromDialog(it)
                } else {
                    resetSelectedProductName()
                }
            }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Party>("party")
            ?.observe(
                viewLifecycleOwner
            ) {
                if (it != null) {
                    viewModel.party= it
                    viewModel.partyList.add(it)
                    setupCustomerAdapter(viewModel.partyList)
                }
            }
        with(viewModel)
        {
            setupGeneralViewModel(this)
            getParty(true)
            getProducts(true)
            productResponse.observe(viewLifecycleOwner)
            {
                it.getContentIfNotHandled()?.let { it1 -> setupProductAdapter(it1) }
            }
            partyResponse.observe(viewLifecycleOwner)
            {
                it.getContentIfNotHandled()?.let { it1 -> setupCustomerAdapter(it1) }

            }

            item.observe(viewLifecycleOwner)
            {
                moveToNextScreen(
                    AddOrderFragmentDirections.actionAddOrderFragmentToAddProductDialog(it)
                )
            }

            btnKey.observe(viewLifecycleOwner)
            {
                if (it == ADD_PRODUCT_BTN) {
                    moveToNextScreen(
                        AddOrderFragmentDirections.actionAddOrderFragmentToAddProductDialog(Item())
                    )
                }else if (it == ADD_CUSTOMER_BTN)
                {
                    moveToNextScreen(AddOrderFragmentDirections.actionAddOrderFragmentToAddCustomerDialog())
                }
            }
            orderStatusResponse.observe(viewLifecycleOwner) {

                showSnackBar("Status Changed Successfully Updated")
            }
        }
    }

    private fun setupProductAdapter(productList: ArrayList<Item>) {

        val nameList = ArrayList<String>()
        val map: MutableMap<String, String> = mutableMapOf()
        for (model in productList) {
            nameList.add(model.Description)
            map[model.Description] = model.Barcode
        }
        binding.itemSpinner.setupAdapter(nameList)
        viewModel.productIDMap = map
    }

    private fun setupCustomerAdapter(customerList: ArrayList<Party>) {

        val nameList = ArrayList<String>()
        val map: MutableMap<String, String> = mutableMapOf()
        for (model in customerList) {
            nameList.add(model.PartyName)
            map[model.PartyName] = model.PartyCode
        }
        binding.customerSpinner.setupAdapter(nameList)
        viewModel.customerIDMap = map
    }


    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddOrderBinding.inflate(layoutInflater, container, false)


    override fun btnListener() {
    }


    override fun onDestroy() {
        super.onDestroy()

        showProgressDialog(false)
    }

    private fun saveItemFromDialog(item: Item) {
        resetSelectedProductName()
        viewModel.addItemToProductAdapterFromDialog(item)
    }

    private fun resetSelectedProductName() {
        viewModel.selectedProductName.set("")
    }
}
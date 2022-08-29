package com.moveitech.riderapp.viewModel


import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moveitech.riderapp.adapter.OrdersAdapter
import com.moveitech.riderapp.adapter.SaleItemAdapter
import com.moveitech.riderapp.dataModel.generalReponse.BaseResponse
import com.moveitech.riderapp.dataModel.item.Item
import com.moveitech.riderapp.dataModel.location.TrackingData
import com.moveitech.riderapp.dataModel.order.Document
import com.moveitech.riderapp.dataModel.order.Order
import com.moveitech.riderapp.dataModel.order.OrderResponse
import com.moveitech.riderapp.dataModel.party.Party
import com.moveitech.riderapp.network.ResultWrapper
import com.moveitech.riderapp.repository.ApiDataRepository
import com.moveitech.riderapp.utils.Constants.Companion.SAVE_ORDER_BTN
import com.moveitech.riderapp.utils.OneShotEvent
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val dataRepository: ApiDataRepository) :
    BaseViewModel() {

     var userID: String = ""
    private var productBarCode: String = ""
    private var productName: String = ""
    private var customerName: String = ""
    val adapter: OrdersAdapter = OrdersAdapter(viewModel = this)
    val itemAdapter: SaleItemAdapter = SaleItemAdapter(viewModel = this)

    val orderResponse: MutableLiveData<OneShotEvent<OrderResponse>> = MutableLiveData()
    val item: MutableLiveData<Item> = MutableLiveData()
    val productResponse: MutableLiveData<OneShotEvent<ArrayList<Item>>> = MutableLiveData()
    val partyResponse: MutableLiveData<OneShotEvent<ArrayList<Party>>> = MutableLiveData()
    val btnAction: MutableLiveData<OneShotEvent<Order>> = MutableLiveData()
    val btnKey: MutableLiveData<Int> = MutableLiveData()
    val orderStatusResponse: MutableLiveData<OneShotEvent<BaseResponse>> = MutableLiveData()
    val trackingResponse: MutableLiveData<OneShotEvent<ArrayList<TrackingData>>> = MutableLiveData()
    val selectedCustomerName: ObservableField<String> = ObservableField("")
    get() {
        if (customerName!= field.get())
        {
            customerName= field.get().toString()
            val customerID= customerIDMap[customerName]
            party= partyList.single { model -> model.PartyCode == customerID }
        }
        return field
    }

    var selectedProductName: ObservableField<String> = ObservableField("")
        get() {
            if (productName != field.get()) {
                productName= field.get().toString()
                productBarCode = productIDMap[field.get()].toString()
                for (model in productList) {
                    if (model.Barcode == productBarCode) {
                        model.UnitCost = model.UnitRetail
                        if (!itemAdapter.checkItemExists(model)) {
                            item.setValue(model)
                        } else {
                            dialogMessage.setValue("Item Already Selected")
                        }
                        break
                    }
                }
            }
            return field
        }

    val subTotalAmount: ObservableField<String> = ObservableField("0")
    val totalQTY: ObservableField<String> = ObservableField("0")
    val discount: ObservableField<String> = ObservableField("0")
    val totalAmount: ObservableField<String> = ObservableField("0")
    var productIDMap: MutableMap<String, String> = mutableMapOf()
    var customerIDMap: MutableMap<String, String> = mutableMapOf()
    var productList = ArrayList<Item>()
    var partyList = ArrayList<Party>()
    var party: Party = Party()
    var order: Order = Order()


    fun onClick(key: Int = 0) {
        if (key== SAVE_ORDER_BTN)
        {
            setupOrder()
        }else
        {
            btnKey.value=key
        }
    }

    fun onClickRemove(item: Item?) {
        if (item != null) {
            itemAdapter.removeItem(item)
        }
    }
    fun onAdapterItemClick(key: Int = 0, model: Order) {

        order = model
        order.btnAction = key
        btnAction.value = OneShotEvent(model)
    }


    fun getOrders(riderCode: String, showProgress: Boolean) {
        viewModelScope.launch {
            showProgressBar(showProgress)
            dataRepository.getOrders(riderCode, "0000000001")
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            orderResponse.value = OneShotEvent(response.value)
                            adapter.setList(response.value.Data)
                        }
                        else -> handleErrorType(response)
                    }
                }
        }
    }

    fun getProducts(showProgress: Boolean) {
        viewModelScope.launch {

            showProgressBar(showProgress)
            dataRepository.getProducts("0000000001")
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            productResponse.value = OneShotEvent(response.value.Data)
                            productList = response.value.Data
                        }
                        else -> handleErrorType(response)
                    }
                }
        }
    }

    fun getParty(showProgress: Boolean) {
        viewModelScope.launch {
            dataRepository.getParty("0000000001")
                .let { response ->

                    when (response) {
                        is ResultWrapper.Success -> {
                            partyResponse.value = OneShotEvent(response.value.Data)
                            partyList= response.value.Data
                        }
                        else -> handleErrorType(response)
                    }
                }
        }
    }

    fun getTrackingData(trackingID: String, showProgress: Boolean) {
        viewModelScope.launch {
            showProgressBar(showProgress)
            dataRepository.getTrackingData(trackingID, "0", "0000000001")
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            trackingResponse.value = OneShotEvent(response.value.Data)
                        }
                        else -> handleErrorType(response)
                    }
                }
        }
    }

    fun saveOrders(
        trackingID: String,
        status: Int,
        showProgress: Boolean
    ) {
        viewModelScope.launch {
            showProgressBar(showProgress)
            dataRepository.saveStatus(trackingID, status, "0000000001")
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            orderStatusResponse.value = OneShotEvent(response.value)
                        }
                        else -> handleErrorType(response)
                    }
                }
        }
    }
    private fun saveOrder(document: Document, showProgress: Boolean) {
        viewModelScope.launch {
            showProgressBar(showProgress)
            dataRepository.saveOrder(document)
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            dialogMessage.value = response.value.Message
                        }
                        else -> handleErrorType(response)
                    }
                }
        }
    }

    fun getDate(): String {
        //date
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(date)
    }

    fun addItemToProductAdapter(item: Item) {
        try {
            val qty: Double = (totalQTY.get()?.toDouble() ?: 0.0) + item.Qty
            val amount: Double = (totalAmount.get()?.toDouble() ?: 0.0) + item.Amount
            totalQTY.set(qty.toString())
            subTotalAmount.set(amount.toString())
            totalAmount.set(amount.toString())
        } catch (e: Exception) {
            Log.e("Conversion error:", e.message!!)
        }
    }

    fun addItemToProductAdapterFromDialog(item:Item) {
        if (!itemAdapter.checkItemExists(item)) {
            try {
                val qty: Double = (totalQTY.get()?.toDouble() ?: 0.0) + item.Qty
                val amount: Double = (totalAmount.get()?.toDouble() ?: 0.0) + item.Amount
                totalQTY.set(qty.toString())
                subTotalAmount.set(amount.toString())
                totalAmount.set(amount.toString())

            } catch (e: java.lang.Exception) {
                Log.e("Conversion error:", e.message!!)
            }
            itemAdapter.addItem(item)


        } else {
            dialogMessage.setValue("Item already Exists")
        }
    }

    private fun setupOrder() {
        val document = Document()
        if (!selectedCustomerName.get().isNullOrEmpty())
        {
            if (totalAmount.get()!="0.0")
            {
                document.apply {
                    DocDate= getDate()
                    PartyAddress = party.PartyAddress
//                    PartyCode = party.PartyCode
                    PartyEmail = party.Email
                    PartyName= party.PartyName
                    PartyMobile =party.Mobile
                    TotalAmount= totalAmount.get()!!.toDouble()
                    Action= "INSERT"
                    DocumentDetail= itemAdapter.list
                    UserId= this@OrderViewModel.userID

                }
                saveOrder(document,true)
            }else
            {
                dialogMessage.value= "Please select or create product"
            }
        }else
        {
            dialogMessage.value= "Please select or create customer"
        }

    }


}
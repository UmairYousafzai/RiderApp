package com.moveitech.riderapp.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.moveitech.riderapp.dataModel.order.Order


@BindingAdapter("setAdapter")
fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = adapter
}
//@BindingAdapter("setCustomText")
//fun setAdapter(btn: MaterialButton, order: Order) {
//    when (order.Status) {
//        "0" -> btn.text = "Pending"
//
//        "1" -> btn.text = "Hold"
//
//        "2" -> btn.text = "Dispatch"
//        "3" -> btn.text = "Complete"
//        "4" -> btn.text = "Cancel"
//        "5" -> btn.text = "Close"
//        else ->
//        {
//            btn.text="Select"
//        }
//    }
//}
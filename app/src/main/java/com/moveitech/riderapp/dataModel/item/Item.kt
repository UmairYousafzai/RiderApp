package com.moveitech.riderapp.dataModel.item

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.moveitech.riderapp.BR
import java.io.Serializable

class Item: BaseObservable(),Serializable {

     val BusinessBarcode: String = ""
     val NativeDescription: String = ""
     val ProductImage: String = ""
     val Status: String = ""
     val Uan: String = ""
     @get:Bindable
     var UnitSize: Double = 0.0
    set(value) {
        if (field!=value)
        {
            field=value
            notifyPropertyChanged(BR.unitSize)

        }
    }
    @get:Bindable
    var Amount: Double = 0.0
        set(value) {
            if (field!=value)
            {
                field=value
                notifyPropertyChanged(BR.amount)

            }
        }
     val Barcode: String = ""
     @get:Bindable
     var  Description: String = ""
     set(value) {
         if(field!=value)
         {
             field= value
             notifyPropertyChanged(BR.description)

         }
     }
    @get:Bindable
     var Qty: Double = 0.0
        set(value) {
            if (field!=value)
            {
                field=value
                Amount = UnitRetail * Qty
                notifyPropertyChanged(BR.qty)

            }
        }
    @get:Bindable
     var UnitCost: Double = 0.0
        set(value) {
            if (field!=value)
            {
                field=value
                notifyPropertyChanged(BR.unitCost)

            }
        }
    @get:Bindable
     var UnitRetail: Double = 0.0
        set(value) {
            if (field!=value)
            {
                field=value
                Amount = UnitRetail * Qty
                notifyPropertyChanged(BR.unitRetail)

            }
        }
}
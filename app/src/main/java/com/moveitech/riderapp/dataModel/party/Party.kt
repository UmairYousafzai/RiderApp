package com.moveitech.riderapp.dataModel.party

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.moveitech.riderapp.BR
import java.io.Serializable

 class Party:Serializable, BaseObservable()
 {
     val CNIC: String = ""
     val ClosingBalance: Double =0.0

     @get:Bindable
     var Email: String = ""
     set(value) {
         if(field!=value)
         {
             field=value
             notifyPropertyChanged(BR.email)
         }
     }

     @get:Bindable
     var Mobile: String = ""
         set(value) {
             if(field!=value)
             {
                 field=value
                 notifyPropertyChanged(BR.mobile)

             }
         }
     val OpeningBalance: Double =0.0
     @get:Bindable
     var PartyAddress: String = ""
         set(value) {
             if(field!=value)
             {
                 field=value
                 notifyPropertyChanged(BR.partyAddress)

             }
         }
     var PartyCode: String = ""
     @get:Bindable
     var PartyName: String = ""
         set(value) {
             if(field!=value)
             {
                 field=value
                 notifyPropertyChanged(BR.partyName)

             }
         }
     val PartyType: String = ""
     val Phone: String = ""
 }
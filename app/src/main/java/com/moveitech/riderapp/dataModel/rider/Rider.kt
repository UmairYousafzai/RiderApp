package com.moveitech.riderapp.dataModel.rider

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import java.io.Serializable
 class Rider:Serializable, BaseObservable()
 {
     var Action: String="INSERT"

     var Address: String=""

     var BusinessId: String="0000000001"

     var Cnic: String=""

     var DOB: String=""

     var DOJ: String=""

     var DOL: String=""
     var Details: String=""

     var Email: String=""

     var Mobile: String=""

     var Password: String=""

     var Phone: String=""
     var RiderCode: String=""
     var RiderName: String=""
     var RoleId: Int=0
     var ShiftEndTime: String=""
     var ShiftStartTime: String=""
     var Status: String="a"
     var UserId: String="0000000001"

     fun map(rider: Rider)
     {
         Action=rider.Action
         Address= rider.Address
         Cnic=rider.Cnic
         DOB=rider.DOB
         DOL=rider.DOL
         DOJ=rider.DOJ
         Details=rider.Details
         Email=rider.Email
         Mobile=rider.Mobile
         Password=rider.Password
         Phone=rider.Phone
         RiderCode=rider.RiderCode
         RiderName=rider.RiderName
         RoleId=rider.RoleId
         ShiftEndTime=rider.ShiftEndTime
         ShiftStartTime=rider.ShiftStartTime
     }
 }
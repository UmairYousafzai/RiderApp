package com.moveitech.riderapp.dataModel.order

import com.moveitech.riderapp.dataModel.item.Item

data class Document(
    var Action: String = "",
    var BusinessId: String = "0000000001",
    var Description: String = "",
    var DocDate: String = "",
    var DocNo: String = "",
    var DocType: String = "2",
    var DocumentDetail: ArrayList<Item> = ArrayList(),
    var PartyAddress: String = "",
    var PartyCity: String = "0001",
    var PartyCityName: String = "",
    var PartyCode: String = "",
    var PartyEmail: String = "",
    var PartyMobile: String = "",
    var PartyName: String = "",
    var SaleType: String = "Mob-Ecom",
    var TotalAmount: Double=0.0,
    var TotalDiscountAmount: Double=0.0,
    var UserId: String = ""
)
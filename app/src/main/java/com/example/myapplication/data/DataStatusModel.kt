package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

//Token that Prince Response
data class DataStatus (
    @SerializedName("order_id")     var order_id: String?  = null,
    @SerializedName("amount")       var amount  : Double?  = null,
    @SerializedName("currency")     var currency: String?  = null,
    @SerializedName("status")       var status: String?  = null,
    @SerializedName("payment_date") var payment_date: String?  = null,
)

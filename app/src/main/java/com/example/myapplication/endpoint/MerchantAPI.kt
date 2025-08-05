package com.example.myapplication.endpoint

import android.util.Base64
import android.util.Base64.encodeToString
import com.example.myapplication.data.TokenRequestModel
import com.example.myapplication.data.TokenResponseModel
import com.example.myapplication.data.TokenResponseStatusModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface MerchantAPI {
    @POST("app-payment")
    fun generateToken(@Body body: TokenRequestModel): Call<TokenResponseModel>

    @POST("qr-payment")
    fun generateQrForPayment(@Body body: TokenRequestModel): Call<ResponseBody>

    @GET("status-payment/{orderId}")
    fun checkQrStatus(@Path("orderId") orderId: String): Call<TokenResponseStatusModel>

    companion object {
        private val endPoint: String get() = "https://dbapi-uat.princebank.com.kh/merchant-online/order/api/v1/"
        private val userName: String get()  = "PrincePay"
        private val password: String get() = "PrincePay@123"
        private val encoded: String get() = "$userName:$password"
        private val basicAuth: String get() = "Basic "+ encodeToString(encoded.toByteArray(), Base64.NO_WRAP)

        private val client = OkHttpClient.Builder()
            .authenticator { _, response ->
                return@authenticator response.request.newBuilder()
                    .addHeader("Authorization", basicAuth)
                    .build()
            }
            .build()
        private val gson = GsonBuilder()
            .setLenient()
            .create()

        private var merchantAPI: MerchantAPI? = null

        fun instance(): MerchantAPI {
            if(merchantAPI == null) {
                merchantAPI = Retrofit.Builder()
                    .baseUrl(endPoint)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build().create(MerchantAPI::class.java)
            }
            return merchantAPI!!
        }

    }
}
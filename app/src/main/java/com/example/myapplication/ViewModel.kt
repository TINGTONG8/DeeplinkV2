package com.example.myapplication

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.TokenRequestModel
import com.example.myapplication.data.TokenResponseModel
import com.example.myapplication.data.TokenResponseStatusModel
import com.example.myapplication.endpoint.MerchantAPI
import com.example.myapplication.utils.hideLoading
import com.example.myapplication.utils.showLoading
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.SecureRandom

data class QrPaymentState(
    val orderNo: String,
    val qrBitmap: ImageBitmap?
)

enum class PaymentStatus {
    Success, Closed, Pending
}

enum class DeepLinkResult {
    Success, Failure
}

class ViewModel : ViewModel() {

    val titleAppBar = mutableIntStateOf(R.string.iphone_16_pro_max)
    val detailImage = mutableIntStateOf(R.drawable.iphone_16_pro_max_detail)
    var price = mutableIntStateOf(5000)
    val totalPrice: Int get() = price.intValue * qty.intValue
    var qty = mutableIntStateOf(1)
    val orderNo = mutableStateOf("")

    val qrPaymentState = mutableStateOf<QrPaymentState?>(null)

    private val _paymentStatusEvent = MutableSharedFlow<PaymentStatus>()
    val paymentStatusEvent = _paymentStatusEvent.asSharedFlow()

    private val _deepLinkResultEvent = MutableSharedFlow<DeepLinkResult>()
    val deepLinkResultEvent = _deepLinkResultEvent.asSharedFlow()

    val intentTitleAppBar = mutableIntStateOf(R.string.successful)
    val intentTitleAppBarColor = mutableIntStateOf(R.color.green)
    val intentStatus = mutableIntStateOf(R.drawable.ic_check)

    private fun generateNewOrderNo() {
        val random = SecureRandom()
        val chars = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray()
        orderNo.value = (1..10).map { chars[random.nextInt(chars.size)] }.joinToString("")
    }

    private fun buildInvoice(): TokenRequestModel {
        return TokenRequestModel(
            transOrderNo = orderNo.value,
            amt = totalPrice,
            currency = "USD",
            remark = "XD",
            notifyUrl = "https://sl.seokey.site/notify.php",
            expireMinutes = 15
        )
    }

    /**
     * Pay by Deeplink
     */
    fun openPrinceAppPayment(onSuccess:(token: String) -> Unit) {
        showLoading()
        generateNewOrderNo()
        MerchantAPI.instance().generateToken(buildInvoice()).enqueue(object :
            Callback<TokenResponseModel> {
            override fun onResponse(
                call: Call<TokenResponseModel>,
                response: Response<TokenResponseModel>
            ) {
                hideLoading()
                if(response.isSuccessful) {
                    response.body()?.data?.token?.let {
                        onSuccess.invoke(it)
                    }
                }
            }

            override fun onFailure(call: Call<TokenResponseModel>, t: Throwable) {
                hideLoading()
                Log.d(javaClass.name, "onFailure: ${t.message}")
            }
        })
    }

    /**
     * Pay by QR
     */
    fun generateQrForPayment(onSuccess: ()->Unit, onFailure: (String?)->Unit = {}) {
        showLoading()
        generateNewOrderNo()
        val invoice = buildInvoice()

        MerchantAPI.instance().generateQrForPayment(invoice).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                hideLoading()
                if (response.isSuccessful) {
                    val bytes = response.body()?.bytes()
                    if (bytes != null) {
                        try {
                            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                            qrPaymentState.value = QrPaymentState(
                                orderNo = orderNo.value,
                                qrBitmap = bitmap.asImageBitmap()
                            )
                            onSuccess.invoke()
                        } catch (e: Exception) {
                            onFailure.invoke("Failed to decode image: ${e.message}")
                        }
                    } else {
                        onFailure.invoke("Empty response body")
                    }
                } else {
                    onFailure.invoke("API Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                hideLoading()
                onFailure.invoke(t.message)
            }
        })
    }

    /**
     * Checking QR Status
     */
    fun checkPaymentStatus() {
        val currentOrderNo = orderNo.value

        if (currentOrderNo.isBlank()) {
            Log.d(javaClass.name, "checkPaymentStatus skipped: orderNo is blank.")
            return
        }

        Log.d(javaClass.name, "Checking status for order: $currentOrderNo")

        MerchantAPI.instance().checkQrStatus(currentOrderNo).enqueue(object : Callback<TokenResponseStatusModel>{
            override fun onResponse(
                call: Call<TokenResponseStatusModel>,
                response: Response<TokenResponseStatusModel>
            ) {
                if (response.isSuccessful) {
                    val statusString = response.body()?.data?.status?.lowercase()
                    Log.d(javaClass.name, "Status check response: $statusString")
                    val status = when(statusString) {
                        "success"-> PaymentStatus.Success
                        "closed"-> PaymentStatus.Closed
                        else -> PaymentStatus.Pending
                    }
                    viewModelScope.launch {
                        _paymentStatusEvent.emit(status)
                    }
                } else {
                    Log.e(javaClass.name, "Status check API call failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TokenResponseStatusModel>, t: Throwable) {
                Log.e(javaClass.name, "onFailure during status check: ${t.message}")
            }
        })
    }

    fun handleDeepLinkResult(status: Int) {
        val result = if (status == 0) DeepLinkResult.Success else DeepLinkResult.Failure
        viewModelScope.launch {
            _deepLinkResultEvent.emit(result)
        }
    }

    fun reset(){
        qty.intValue = 1
        qrPaymentState.value = null
        orderNo.value = ""
    }
}
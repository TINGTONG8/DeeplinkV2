package com.example.myapplication.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.UiController
import com.example.myapplication.ViewModel
import com.example.myapplication.utils.AddMinusComponent
import com.example.myapplication.utils.BottomSheetComponent
import com.example.myapplication.utils.ButtonComponent
import com.example.myapplication.utils.safeNavigate
import androidx.core.net.toUri
import com.example.myapplication.PaymentStatus
import com.example.myapplication.utils.LoadingDialogComponent
import com.example.myapplication.utils.loadingFlow
import kotlinx.coroutines.delay

private const val TIMER_DURATION_SECONDS = 180

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ViewModelConstructorInComposable")
@Composable
fun DetailPage(
    navController: NavController,
    vm: ViewModel
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val showQRBottomSheet = remember { mutableStateOf(false) }
    val timeLeftInSeconds = remember { mutableIntStateOf(TIMER_DURATION_SECONDS) }
    val isLoading by loadingFlow.collectAsState()

    LaunchedEffect(
        key1 = timeLeftInSeconds.intValue,
        key2 = showQRBottomSheet.value,
        key3 = isLoading
    ) {
        if (showQRBottomSheet.value && timeLeftInSeconds.intValue > 0 && !isLoading) {
            val secondsRemaining = timeLeftInSeconds.intValue
            if (secondsRemaining % 15 == 0) {
                vm.checkPaymentStatus()
            }

            delay(1000L)
            timeLeftInSeconds.intValue--
        }
    }

    LaunchedEffect(Unit) {
        vm.paymentStatusEvent.collect { status ->
            when (status) {
                PaymentStatus.Success -> {
                    showQRBottomSheet.value = false
                    vm.intentTitleAppBar.intValue = R.string.successful
                    vm.intentTitleAppBarColor.intValue = R.color.green
                    vm.intentStatus.intValue = R.drawable.ic_check
                    navController.navigate("intent_page_route") {
                        popUpTo("detail_page_route") { inclusive = true }
                    }
                }
                PaymentStatus.Closed -> {
                    showQRBottomSheet.value = false
                    vm.intentTitleAppBar.intValue = R.string.failure
                    vm.intentTitleAppBarColor.intValue = R.color.red
                    vm.intentStatus.intValue = R.drawable.ic_x
                    Toast.makeText(context, "Payment Closed or Failed", Toast.LENGTH_LONG).show()
                }
                PaymentStatus.Pending -> {
                    Toast.makeText(context, "Scan QR to pay.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    UiController(useDarkIcons = true)

    LaunchedEffect (Unit) {
        vm.reset()
    }

    BackHandler(enabled = isLoading) {}

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(
                            modifier = Modifier,
                            onClick = {
                                navController.safeNavigate("list_page_route")
                            }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(50.dp),
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Pop back"
                            )
                        }
                    },
                    title = {
                        Text(
                            stringResource(vm.titleAppBar.intValue),
                            fontSize = 20.sp,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colorResource(R.color.gray)
                    )
                )
            }

        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.white))
                    .padding(innerPadding)
                    .padding(horizontal = 15.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            focusManager.clearFocus()
                        }
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 25.dp),
                        painter = painterResource(vm.detailImage.intValue),
                        contentDescription = "--",
                        contentScale = ContentScale.Fit
                    )
                }

                Text(
                    "$ ${vm.totalPrice}.00",
                    modifier = Modifier
                        .padding(top = 16.dp),
                    fontSize = 30.sp,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
                AddMinusComponent(
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    onMinusClick = {
                        if (vm.qty.intValue > 1)
                            vm.qty.intValue--
                    },
                    qtyText = vm.qty.intValue,
                    onAddClick = {
                        vm.qty.intValue++
                    }
                )
                ButtonComponent(
                    modifier = Modifier,
                    buttonText = stringResource(R.string.buy_deep_link),
                    buttonColor = colorResource(R.color.gray),
                    onClick = {
                        vm.openPrinceAppPayment { token ->
                            val browserIntent = Intent(Intent.ACTION_VIEW,
                                "https://link-uat.princebank.com.kh/sdk?orderNo=${token}&appPkg=Y29tLmV4YW1wbGUubXlhcHBsaWNhdGlvbg==&scheme=bXlhcHBzLWNoZW1l".toUri())
                            context.startActivity(browserIntent)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ButtonComponent(
                    modifier = Modifier,
                    buttonText = stringResource(R.string.buy_qr),
                    buttonColor = colorResource(R.color.gray),
                    onClick = {
                        vm.generateQrForPayment(
                            onSuccess = {
                                timeLeftInSeconds.intValue = TIMER_DURATION_SECONDS
                                showQRBottomSheet.value = true
                            },
                            onFailure = { errorMessage ->
                                Toast.makeText(context, errorMessage ?: "Failed to get QR code", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (showQRBottomSheet.value) {
            vm.qrPaymentState.value?.let { state ->
                BottomSheetComponent(
                    modifier = Modifier,
                    qrBitmap = state.qrBitmap,
                    orderNo = state.orderNo,
                    model = vm.titleAppBar.intValue,
                    qty = vm.qty.intValue,
                    amount = vm.totalPrice,
                    timeLeftInSeconds = timeLeftInSeconds.intValue,
                    onDismiss = {
                        showQRBottomSheet.value = false
                    },
                    onRepay = {
                        vm.generateQrForPayment(
                            onSuccess = {
                                timeLeftInSeconds.intValue = TIMER_DURATION_SECONDS
                            },
                            onFailure = { errorMessage ->
                                Toast.makeText(context, errorMessage ?: "Failed to get QR code", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                )
            }
        }

        if (isLoading) {
            LoadingDialogComponent()
        }
    }
}
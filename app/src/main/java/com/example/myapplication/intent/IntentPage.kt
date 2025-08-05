package com.example.myapplication.intent

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
import com.example.myapplication.PaymentStatus
import com.example.myapplication.R
import com.example.myapplication.UiController
import com.example.myapplication.ViewModel
import com.example.myapplication.utils.ButtonComponent
import com.example.myapplication.utils.IntentComponent
import com.example.myapplication.utils.safeNavigate

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ViewModelConstructorInComposable")
@Composable
fun IntentPage(
    navController: NavController,
    vm: ViewModel
) {
    val focusManager = LocalFocusManager.current

    UiController(useDarkIcons = true)

    BackHandler {
        navController.safeNavigate("list_page_route")
    }

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
                        stringResource(vm.intentTitleAppBar.intValue),
                        color = colorResource(vm.intentTitleAppBarColor.intValue),
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
            Image(
                modifier = Modifier
                    .size(160.dp)
                    .padding(vertical = 30.dp),
                painter = painterResource(vm.intentStatus.intValue),
                contentDescription = "--",
                contentScale = ContentScale.Fit
            )
            IntentComponent (
                modifier = Modifier,
                orderNo = vm.orderNo.value,
                model = vm.titleAppBar.intValue,
                qty =  vm.qty.intValue,
                amount = vm.totalPrice
            )
            Spacer(modifier = Modifier.weight(1f))
            ButtonComponent(
                modifier = Modifier
                    .padding(bottom = 30.dp),
                buttonText = stringResource(R.string.done),
                buttonColor = colorResource(R.color.gray),
                onClick = {
                    navController.safeNavigate("list_page_route")
                }
            )
        }
    }
}
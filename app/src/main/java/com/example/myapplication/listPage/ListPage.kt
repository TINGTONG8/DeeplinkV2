package com.example.myapplication.listPage

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.myapplication.utils.ListComponent
import com.example.myapplication.utils.ListComponentV2
import com.example.myapplication.utils.safeNavigate
import kotlin.system.exitProcess

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ViewModelConstructorInComposable")
@Composable
fun ListPage(
    navController: NavController,
    vm: ViewModel
) {
    val focusManager = LocalFocusManager.current

    UiController(useDarkIcons = true)

    BackHandler {
        exitProcess(0)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.shop),
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
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            ListComponent (
                modifier = Modifier,
                title = stringResource(R.string.iphone_16_pro_max),
                iconRight = painterResource(R.drawable.iphone_list),
                onClick = {
                    vm.titleAppBar.intValue = R.string.iphone_16_pro_max
                    vm.detailImage.intValue = R.drawable.iphone_16_pro_max_detail
                    vm.price.intValue = 2000
                    navController.safeNavigate("detail_page_route")
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ListComponent (
                modifier = Modifier,
                title = stringResource(R.string.galaxy_s24),
                iconLeft = painterResource(R.drawable.galaxy_s24_list),
                onClick = {
                    vm.titleAppBar.intValue = R.string.galaxy_s24
                    vm.detailImage.intValue = R.drawable.galaxy_s24_detail
                    vm.price.intValue = 1500
                    navController.safeNavigate("detail_page_route")
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ListComponentV2 (
                modifier = Modifier,
                title = stringResource(R.string.apple_watch_series_10_),
                iconLeft = painterResource(R.drawable.apple_watch_series_10_list),
                onClick = {
                    vm.titleAppBar.intValue = R.string.apple_watch_series_10
                    vm.detailImage.intValue = R.drawable.apple_watch_series_10_detail
                    vm.price.intValue = 500
                    navController.safeNavigate("detail_page_route")
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ListComponentV2 (
                modifier = Modifier,
                modifierAlign = Alignment.CenterEnd,
                title = stringResource(R.string.galaxy_buds_3_pro),
                titleAlign = TextAlign.End,
                iconRight = painterResource(R.drawable.galaxy_buds_3_pro_list),
                onClick = {
                    vm.titleAppBar.intValue = R.string.galaxy_buds_3_pro
                    vm.detailImage.intValue = R.drawable.galaxy_buds_3_pro_detail
                    vm.price.intValue = 250
                    navController.safeNavigate("detail_page_route")
                }
            )
        }
    }
}
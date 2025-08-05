package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.detail.DetailPage
import com.example.myapplication.intent.IntentPage
import com.example.myapplication.listPage.ListPage
import com.example.myapplication.utils.LoadingDialogComponent
import com.example.myapplication.utils.loadingFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val sharedViewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppNavigation(sharedViewModel)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.data == null) {
            return
        }

        val status = intent.data?.getQueryParameter("status")?.toIntOrNull() ?: 1
        sharedViewModel.handleDeepLinkResult(status)
    }
}

@Composable
fun AppNavigation(sharedViewModel: ViewModel) {
    UiController(useDarkIcons = true)

    val navController = rememberNavController()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        launch {
            sharedViewModel.deepLinkResultEvent.collect { result ->
                when (result) {
                    DeepLinkResult.Success -> {
                        navController.navigate("intent_page_route") {
                            popUpTo("list_page_route") { inclusive = false }
                        }
                    }
                    DeepLinkResult.Failure -> {
                        navController.navigate("list_page_route") {
                            popUpTo("list_page_route") { inclusive = true }
                        }
                        Toast.makeText(context, "Payment Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    val isLoading by loadingFlow.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = "list_page_route"
        ) {
            composable("list_page_route") {
                ListPage(navController = navController, vm = sharedViewModel)
            }
            composable("detail_page_route") {
                DetailPage(navController = navController, vm = sharedViewModel)
            }
            composable("intent_page_route") {
                IntentPage(navController = navController, vm = sharedViewModel)
            }
        }
        if (isLoading) {
            LoadingDialogComponent()
        }
    }
}
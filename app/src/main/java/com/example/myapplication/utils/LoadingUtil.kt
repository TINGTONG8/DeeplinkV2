package com.example.myapplication.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Coroutines scope for loading dialog
 */
private val scope = CoroutineScope(Dispatchers.Main)

/**
 * SharedFlow for loading dialog
 */
val loadingFlow = MutableStateFlow(false)

/**
 * Show loading dialog
 */
fun showLoading() {
    scope.launch { loadingFlow.emit(true) }
}

/**
 * Hide loading dialog
 */
fun hideLoading() {
    scope.launch { loadingFlow.emit(false) }
}
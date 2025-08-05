package com.example.myapplication.utils

import androidx.navigation.NavController

fun NavController.safeNavigate(route: String) {
    val currentRoute = this.currentDestination?.route
    if (currentRoute != route) {
        this.navigate(route)
    }
}
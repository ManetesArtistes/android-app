package com.example.manetes_artistes_app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun isWifiConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

    return networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
}

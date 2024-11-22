package com.example.manetes_artistes_app.utils

import android.content.Context
import android.provider.Settings

fun generateDeviceUniqueIdentifier(context: Context): String {
    return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}

fun generateStatsFileName(context: Context): String {
    return "stats_${generateDeviceUniqueIdentifier(context)}.json"
}
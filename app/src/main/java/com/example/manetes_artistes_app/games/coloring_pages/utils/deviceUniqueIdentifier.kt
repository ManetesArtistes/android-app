package com.example.manetes_artistes_app.games.coloring_pages.utils

import android.content.Context
import android.provider.Settings

fun generateDeviceUniqueIdentifier(context: Context): String {
    return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}
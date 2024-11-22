package com.example.manetes_artistes_app.utils

import android.content.Context
import com.example.manetes_artistes_app.stats.Center
import com.google.gson.Gson
import java.io.File

fun centersToJson(centers: MutableList<Center>): String {
    val gson = Gson()
    return gson.toJson(centers)
}

fun saveJsonToFile(context: Context, json: String, fileName: String): File {
    // Use the app's internal storage directory (persistent storage)
    val file = File(context.filesDir, fileName)

    // Write the JSON string to the file
    file.writeText(json)

    // Return the File object for further use
    return file
}

fun loadJsonFromFile(context: Context, fileName: String): String? {
    val file = File(context.filesDir, fileName)

    return if (file.exists()) {
        file.readText()
    } else {
        null
    }
}

fun fromJson(json: String): MutableList<Center> {
    val gson = Gson()
    val centerListType = object : com.google.gson.reflect.TypeToken<MutableList<Center>>() {}.type
    return gson.fromJson(json, centerListType)
}

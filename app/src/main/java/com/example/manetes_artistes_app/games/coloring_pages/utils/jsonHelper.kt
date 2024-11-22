package com.example.manetes_artistes_app.games.coloring_pages.utils

import android.content.Context
import com.example.manetes_artistes_app.games.coloring_pages.stats.Center
import com.google.gson.Gson
import java.io.File

fun toJson(centers: MutableList<Center>): String {
    val gson = Gson()
    return gson.toJson(centers)
}

fun saveJsonToFile(context: Context, json: String, fileName: String): File {
    // Use the app's cache directory (writable and temporary storage)
    val file = File(context.cacheDir, fileName)

    // Write the JSON string to the file
    file.writeText(json)

    // Return the File object for further use
    return file
}
fun fromJson(json: String): MutableList<Center> {
    val gson = Gson()
    val centerListType = object : com.google.gson.reflect.TypeToken<MutableList<Center>>() {}.type
    return gson.fromJson(json, centerListType)
}

package com.example.manetes_artistes_app.games.coloring_pages.utils

import com.example.manetes_artistes_app.games.coloring_pages.stats.Center
import com.google.gson.Gson

fun toJson(centers: MutableList<Center>): String {
    val gson = Gson()
    return gson.toJson(centers)
}

fun fromJson(json: String): MutableList<Center> {
    val gson = Gson()
    val centerListType = object : com.google.gson.reflect.TypeToken<MutableList<Center>>() {}.type
    return gson.fromJson(json, centerListType)
}

package com.example.manetes_artistes_app.menus.stickers


import android.content.Context
import com.example.manetes_artistes_app.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StickerLoader {


    private val stickersMap: HashMap<Int, Sticker> = HashMap()
    private var stickersList: List<Sticker> = listOf()

    // Load animals into the HashMap from JSON file
    fun loadStickers(context: Context) {
        if (stickersMap.isNotEmpty()) return

        val inputStream = context.resources.openRawResource(R.raw.stickers)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val gson = Gson();

        val stickerListType = object : TypeToken<List<Sticker>>() {}.type
        stickersList = gson.fromJson(jsonString, stickerListType)
        println("++++++ stickers list init +++++")
        println(stickersList)
        // Convert the List<com.example.manetes_artistes_app.menus.stickers.Sticker> to a HashMap<Int, com.example.manetes_artistes_app.menus.stickers.Sticker>
        stickersMap.putAll(stickersList.associateBy { it.id })
        println(stickersMap)
    }


    // Function to get a sticker by ID
    fun getStickerById(id: Int, context: Context): Sticker? {
        loadStickers(context)
        return stickersMap[id]
    }

    // Function to get all stickers
    fun getAllStickers(context: Context): List<Sticker> {
        loadStickers(context)
        return stickersList
    }
}
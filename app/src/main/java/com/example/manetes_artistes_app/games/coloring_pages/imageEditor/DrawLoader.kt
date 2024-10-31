package com.example.manetes_artistes_app.imageEditor


import android.content.Context
import com.example.manetes_artistes_app.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DrawLoader {
    private val drawsMap: HashMap<Int, Draw> = HashMap()
    private var drawsList: List<Draw> = listOf()

    // Load draws into the HashMap from JSON file
    fun loadDraws(context: Context) {
        if (drawsMap.isNotEmpty()) return

        val inputStream = context.resources.openRawResource(R.raw.draws)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val gson = Gson()

        val drawListType = object : TypeToken<List<Draw>>() {}.type
        drawsList = gson.fromJson(jsonString, drawListType)
        println("++++++ draws list init +++++")
        println(drawsList)
        // Convert the List<Draw> to a HashMap<Int, Draw>
        drawsMap.putAll(drawsList.associateBy { it.id })
        println(drawsMap)
    }


    // Function to get a draw by ID
    fun getDrawById(id: Int,context: Context): Draw? {
        loadDraws(context)
        return drawsMap[id]
    }

    // Function to get all draws
    fun getAllDraws(context: Context): List<Draw> {
        loadDraws(context)
        return drawsList
    }
}
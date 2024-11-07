package com.example.manetes_artistes_app.imageEditor


import Animal
import android.content.Context
import com.example.manetes_artistes_app.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AnimalLoader {


    private val animalsMap: HashMap<Int, Animal> = HashMap()
    private var animalsList: List<Animal> = listOf()

    // Load animals into the HashMap from JSON file
    fun loadAnimals(context: Context) {
        if (animalsMap.isNotEmpty()) return

        val inputStream = context.resources.openRawResource(R.raw.animals)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val gson = Gson()

        val animalListType = object : TypeToken<List<Animal>>() {}.type
        animalsList = gson.fromJson(jsonString, animalListType)
        println("++++++ animals list init +++++")
        println(animalsList)
        // Convert the List<Draw> to a HashMap<Int, Draw>
        animalsMap.putAll(animalsList.associateBy { it.id })
        println(animalsMap)
    }


    // Function to get a draw by ID
    fun getAnimalById(id: Int,context: Context): Animal? {
        loadAnimals(context)
        return animalsMap[id]
    }

    // Function to get all draws
    fun getAllAnimals(context: Context): List<Animal> {
        loadAnimals(context)
        return animalsList
    }
}
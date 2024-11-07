package com.example.manetes_artistes_app.menus

import Animal
import AnimalsAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manetes_artistes_app.R
import com.example.manetes_artistes_app.common.ActivitiesIntentKeys
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity
import com.example.manetes_artistes_app.games.coloring_pages.activities.ImageEditorActivity
import com.example.manetes_artistes_app.imageEditor.AnimalLoader
import com.example.manetes_artistes_app.imageEditor.DrawLoader

class AnimalSelectorActivity : ImmersiveCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_animal_selector)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val listView = findViewById<RecyclerView>(R.id.animalSelectorView)
        val layoutManager = GridLayoutManager(this, 4)
        val animals = AnimalLoader.getAllAnimals(this)

        listView.layoutManager = layoutManager
        listView.adapter = AnimalsAdapter(this, animals)
    }

    private fun onAnimalSelected(animal: Animal) {
        val intent = Intent(this, ImageEditorActivity::class.java)
        DrawLoader.loadDraws(this)
        intent.putExtra(ActivitiesIntentKeys.ANIMAL, animal)
        startActivity(intent)
    }
}
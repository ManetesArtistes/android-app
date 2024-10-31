package com.example.manetesartistes_game.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manetesartistes_game.R
import com.example.manetesartistes_game.common.ActivitiesIntentKeys
import com.example.manetesartistes_game.imageEditor.Draw
import com.example.manetesartistes_game.imageEditor.DrawLoader
import com.example.manetesartistes_game.imageEditor.ImageListAdapter

class ImageListActivity: AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val listView = findViewById<RecyclerView>(R.id.imagesListReciclerView)

        val layoutManager = GridLayoutManager(this, 4)

        val draws = DrawLoader.getAllDraws(this)
        listView.layoutManager = layoutManager
        listView.adapter = ImageListAdapter(this, draws)
        { selectedDraw ->
            onDrawSelected(selectedDraw)
        }
    }

    private fun onDrawSelected(draw: Draw) {
        val intent = Intent(this, ImageEditorActivity::class.java)
        DrawLoader.loadDraws(this)
        intent.putExtra(ActivitiesIntentKeys.DRAW_DATA, draw)
        startActivity(intent)
    }
}
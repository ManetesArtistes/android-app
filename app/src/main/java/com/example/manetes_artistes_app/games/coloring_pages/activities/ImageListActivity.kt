package com.example.manetes_artistes_app.games.coloring_pages.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manetes_artistes_app.R
import com.example.manetes_artistes_app.common.ActivitiesIntentKeys
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity
import com.example.manetes_artistes_app.imageEditor.Draw
import com.example.manetes_artistes_app.imageEditor.DrawLoader
import com.example.manetes_artistes_app.imageEditor.ImageListAdapter
import com.example.manetes_artistes_app.menus.MainMenuActivity

class ImageListActivity: ImmersiveCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        setupRecyclerView()
        addBackClickListener()
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

    private fun addBackClickListener(){
        val doneButton = findViewById<ImageButton>(R.id.backBtn)

        doneButton.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }
}
package com.example.manetes_artistes_app.games.coloring_pages.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manetes_artistes_app.R
import com.example.manetes_artistes_app.common.ActivitiesIntentKeys
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity
import com.example.manetes_artistes_app.imageEditor.Draw
import com.example.manetes_artistes_app.imageEditor.DrawLoader
import com.example.manetes_artistes_app.games.coloring_pages.imageEditor.ImageListAdapter

class ImageListActivity: ImmersiveCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        setupRecyclerView()
        addBackClickListener()
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val listView = findViewById<RecyclerView>(R.id.imagesListReciclerView)

        val layoutManager = object : GridLayoutManager(this, 4){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        val draws = DrawLoader.getAllDraws(this)
        listView.layoutManager = layoutManager

        listView.adapter = ImageListAdapter(
            this,
            draws,
            resources,
            packageName
        )
        { selectedDraw ->
            onDrawSelected(selectedDraw)
        }
    }

    private fun onDrawSelected(draw: Draw) {
        val intent = Intent(this, ImageEditorActivity::class.java)
        DrawLoader.loadDraws(this)
        reproduceSound(R.raw.activity)
        intent.putExtra(ActivitiesIntentKeys.DRAW_DATA, draw)
        startActivity(intent)
    }

    private fun addBackClickListener(){
        val doneButton = findViewById<ImageButton>(R.id.backBtn)

        doneButton.setOnClickListener {
            reproduceSound(R.raw.activity)
            finish()
        }
    }
}
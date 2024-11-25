package com.example.manetes_artistes_app.menus

import Sticker
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import com.example.manetes_artistes_app.R
import com.example.manetes_artistes_app.common.ActivitiesIntentKeys
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity
import com.example.manetes_artistes_app.games.coloring_pages.activities.ImageListActivity
import com.example.manetes_artistes_app.games.simon_says.activities.MainActivitySimonSays
import com.example.manetes_artistes_app.stats.StatsState

class MainMenuActivity : ImmersiveCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)

        val coloringButton = findViewById<ImageButton>(R.id.mainColoringButton)
        val simonButton = findViewById<ImageButton>(R.id.mainSimonButton)
        val changeStickerButton = findViewById<ImageButton>(R.id.changeStickerButton)

        val sticker = intent.getSerializableExtra(ActivitiesIntentKeys.STICKER) as Sticker
        changeStickerButton.setBackgroundResource(resources.getIdentifier(sticker.image, "drawable", packageName))

        coloringButton.setOnClickListener {
            val intent = Intent(this, ImageListActivity::class.java)
            startActivity(intent)
        }

        simonButton.setOnClickListener {
            val intent = Intent(this, MainActivitySimonSays::class.java)
            startActivity(intent)
        }

        changeStickerButton.setOnClickListener {
            finish()
        }
    }
}
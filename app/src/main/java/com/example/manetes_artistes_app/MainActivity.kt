package com.example.manetes_artistes_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity
import com.example.manetes_artistes_app.games.simon_says.activities.MainActivitySimonSays
import com.example.manetes_artistes_app.menus.StickerSelectorActivity

class MainActivity : ImmersiveCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btn = findViewById<ImageButton>(R.id.onlyButton)

        btn.setOnClickListener {
            val intent = Intent(this, StickerSelectorActivity::class.java)
            startActivity(intent)
        }
    }
}
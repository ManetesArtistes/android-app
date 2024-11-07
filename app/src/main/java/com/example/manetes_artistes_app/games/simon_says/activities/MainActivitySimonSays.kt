package com.example.manetes_artistes_app.games.simon_says

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import com.example.manetes_artistes_app.MainActivity
import com.example.manetes_artistes_app.R
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity

class MainActivitySimonSays : ImmersiveCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_simon_says)

        val playBtn = findViewById<Button>(R.id.playBtn)
        val backBtn = findViewById<ImageButton>(R.id.backBtn)

        playBtn.setOnClickListener {
            val intent = Intent(this, GameActivitySimonSays::class.java)
            startActivity(intent)
        }

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
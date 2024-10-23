package com.example.manetes_artistes_app.games.simon_says

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.manetes_artistes_app.R

class MainActivitySimonSays : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_simon_says)

        val btnPlay = findViewById<TextView>(R.id.btnPlay)


        btnPlay.setOnClickListener {
            val intent = Intent(this, GameActivitySimonSays::class.java)
            startActivity(intent)
        }


    }
}
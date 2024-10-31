package com.example.manetes_artistes_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.manetes_artistes_app.common.ActivitiesIntentKeys
import com.example.manetes_artistes_app.games.coloring_pages.activities.ImageListActivity
import com.example.manetes_artistes_app.games.simon_says.GameActivitySimonSays
import com.example.manetes_artistes_app.games.simon_says.MainActivitySimonSays

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val intent = Intent(this, ImageListActivity::class.java)
        intent.putExtra(ActivitiesIntentKeys.USER_ID, 1)
        startActivity(intent)
//        val btnPinta = findViewById<TextView>(R.id.pinta)
//        val btnSimo = findViewById<TextView>(R.id.simo)
//
//
//        btnPinta.setOnClickListener {
//            val intent = Intent(this, MainActivityColoringPages::class.java)
//            startActivity(intent)
//        }
//
//        btnSimo.setOnClickListener {
//            val intent = Intent(this, MainActivitySimonSays::class.java)
//            startActivity(intent)
//        }


    }
}
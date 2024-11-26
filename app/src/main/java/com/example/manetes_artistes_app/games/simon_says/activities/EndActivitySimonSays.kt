package com.example.manetes_artistes_app.games.simon_says.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.manetes_artistes_app.R
import com.example.manetes_artistes_app.common.ActivitiesIntentKeys
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity
import com.example.manetes_artistes_app.menus.MainMenuActivity

class EndActivitySimonSays : ImmersiveCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_end_simon_says)

        val score = intent.getSerializableExtra(ActivitiesIntentKeys.SIMON_SCORE)
        val lblScore = findViewById<TextView>(R.id.simoneScore)

        lblScore.text = score.toString()

        val exitBtn = findViewById<ImageButton>(R.id.exitButton)

        exitBtn.setOnClickListener {
            finish()
        }
    }
}
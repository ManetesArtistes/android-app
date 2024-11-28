package com.example.manetes_artistes_app.games.simon_says.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import com.example.manetes_artistes_app.R
import com.example.manetes_artistes_app.common.ActivitiesIntentKeys
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity

class EndActivitySimonSays : ImmersiveCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_end_simon_says)

        val score = intent.getSerializableExtra(ActivitiesIntentKeys.SIMON_SCORE)
        val lblScore = findViewById<TextView>(R.id.simoneScore)

        lblScore.text = score.toString()

        val exitBtn = findViewById<TextView>(R.id.exitButton)
        exitBtn.text = "SORTIR"

        exitBtn.setOnClickListener {
            finish()
        }
    }
}
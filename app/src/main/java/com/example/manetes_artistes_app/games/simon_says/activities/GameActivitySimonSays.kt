package com.example.manetes_artistes_app.games.simon_says

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import com.example.manetes_artistes_app.R
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity

class GameActivitySimonSays : ImmersiveCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_simon_says)

        val simonBlueBtn = SimonButton(findViewById<ImageButton>(R.id.blueBtn), R.drawable.simon_says_blue_button_on, R.drawable.simon_says_blue_button_off, this, R.raw.simon_blue_button_sound)
        val simonRedBtn = SimonButton(findViewById<ImageButton>(R.id.redBtn), R.drawable.simon_says_red_button_on, R.drawable.simon_says_red_button_off, this, R.raw.simon_red_button_sound)
        val simonGreenBtn = SimonButton(findViewById<ImageButton>(R.id.greenBtn), R.drawable.simon_says_green_button_on, R.drawable.simon_says_green_button_off, this, R.raw.simon_green_button_sound)
        val simonYellowBtn = SimonButton(findViewById<ImageButton>(R.id.yellowBtn), R.drawable.simon_says_yellow_button_on, R.drawable.simon_says_yellow_button_off, this, R.raw.simon_yellow_button_sound)
        val lblScore = findViewById<TextView>(R.id.score)
        val backBtn = findViewById<ImageButton>(R.id.backBtn)

        val game = SimonGame(simonYellowBtn, simonBlueBtn, simonRedBtn, simonGreenBtn, lblScore)

        game.newLevel()


        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivitySimonSays::class.java)
            startActivity(intent)
        }

        simonYellowBtn.button.setOnClickListener {
            simonYellowBtn.activate(game)
            game.check(1)
        }

        simonBlueBtn.button.setOnClickListener {
            simonBlueBtn.activate(game)
            game.check(2)
        }

        simonRedBtn.button.setOnClickListener {
            simonRedBtn.activate(game)
            game.check(3)
        }

        simonGreenBtn.button.setOnClickListener {
            simonGreenBtn.activate(game)
            game.check(4)
        }
    }
}
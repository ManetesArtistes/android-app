package com.example.manetes_artistes_app.games.simon_says.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import com.example.manetes_artistes_app.MainActivity
import com.example.manetes_artistes_app.R
import com.example.manetes_artistes_app.common.ActivitiesIntentKeys
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity
import com.example.manetes_artistes_app.menus.MainMenuActivity
import com.example.manetes_artistes_app.user.User

class MainActivitySimonSays : ImmersiveCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_simon_says)

        val user = User.extractUserFromIntent(intent)
        val playBtn = findViewById<ImageButton>(R.id.playBtn)
        val backBtn = findViewById<ImageButton>(R.id.backBtn)

        playBtn.setOnClickListener {
            val intent = Intent(this, GameActivitySimonSays::class.java)
            intent.putExtra(ActivitiesIntentKeys.USER, user)
            startActivity(intent)
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
}
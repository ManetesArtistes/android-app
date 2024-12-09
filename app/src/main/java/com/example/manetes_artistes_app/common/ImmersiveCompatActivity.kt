package com.example.manetes_artistes_app.common

import android.content.Context
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.manetes_artistes_app.R

abstract class ImmersiveCompatActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    fun reproduceSound(soundSource: Int){
        val sound: MediaPlayer = MediaPlayer.create(this, soundSource)
        sound.start()
        sound.setOnCompletionListener {
            sound.release()
        }
    }
}
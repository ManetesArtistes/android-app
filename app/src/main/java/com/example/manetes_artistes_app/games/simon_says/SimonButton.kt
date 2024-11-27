package com.example.manetes_artistes_app.games.simon_says

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton

class SimonButton(
    val button: ImageButton,
    private val onImage: Int,
    private val offImage: Int,
    private val obj: Any?,
    private val soundSource: Int
) {
    fun activate(game: SimonGame) {
        val sound: MediaPlayer = MediaPlayer.create(this.obj as Context?, this.soundSource)
        
        game.disableButtons()
        button.setBackgroundResource(this.onImage)
        sound.start()

        Handler(Looper.getMainLooper()).postDelayed({
            viewDeactivate()
        }, 1000)

        sound.setOnCompletionListener {
            sound.release()
        }
    }

    fun viewDeactivate(){
        button.setBackgroundResource(this.offImage)
    }
}

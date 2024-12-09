package com.example.manetes_artistes_app.games.simon_says.game

import android.content.Context
import android.media.MediaPlayer
import android.widget.ImageButton
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SimonButton(
    private val lifecycleOwner: LifecycleOwner,
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

        lifecycleOwner.lifecycleScope.launch {
            delay(1000)
            viewDeactivate()
        }

        sound.setOnCompletionListener {
            sound.release()
        }
    }

    fun viewDeactivate(){
        button.setBackgroundResource(this.offImage)
    }
}

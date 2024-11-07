package com.example.manetes_artistes_app.games.simon_says

import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.manetes_artistes_app.R
import com.example.manetes_artistes_app.games.simon_says.activities.GameActivitySimonSays
import kotlin.random.Random



class SimonGame(
    val simonYellowBtn: SimonButton,
    val simonBlueBtn: SimonButton,
    val simonRedBtn: SimonButton,
    val simonGreenBtn: SimonButton,
    val lblScore: TextView,
    val onGameEnd: () -> Unit
){
    var moves: ArrayList<Int> = ArrayList()
    private var currentPlay = 0
    private var score = 0

    fun disableButtons(){
        simonYellowBtn.button.isEnabled = false
        simonBlueBtn.button.isEnabled = false
        simonRedBtn.button.isEnabled = false
        simonGreenBtn.button.isEnabled = false
    }

    fun enableButtons(){
        simonYellowBtn.button.isEnabled = true
        simonBlueBtn.button.isEnabled = true
        simonRedBtn.button.isEnabled = true
        simonGreenBtn.button.isEnabled = true
    }

    fun newLevel(){
        var delay = 0
        val randomNumber = Random.nextInt(1, 5)
        this.moves.add(randomNumber)

        this.currentPlay = 0
        lblScore.text = score.toString()

        disableButtons()

        for (i in this.moves){
            delay += 1500
            Handler(Looper.getMainLooper()).postDelayed({
                when (i) {
                    1 -> simonYellowBtn.activate(this)
                    2 -> simonBlueBtn.activate(this)
                    3 -> simonRedBtn.activate(this)
                    4 -> simonGreenBtn.activate(this)
                }
            }, delay.toLong())
        }

        Handler(Looper.getMainLooper()).postDelayed({
            enableButtons()
        }, delay + 1500L)
    }

    fun check(selection: Int){
        if (selection == moves[currentPlay]){
            currentPlay++
            if (currentPlay == moves.size){
                score++
                newLevel()
            }
            Handler(Looper.getMainLooper()).postDelayed({
                enableButtons()
            },1100)
        }else{
            onGameEnd()
        }
    }
}
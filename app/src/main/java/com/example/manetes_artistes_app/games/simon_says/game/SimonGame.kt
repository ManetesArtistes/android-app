package com.example.manetes_artistes_app.games.simon_says.game

import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class SimonGame(
    private val lifecycleOwner: LifecycleOwner,
    val simonYellowBtn: SimonButton,
    val simonBlueBtn: SimonButton,
    val simonRedBtn: SimonButton,
    val simonGreenBtn: SimonButton,
    val lblScore: TextView,
    val onGameEnd: (score: Int) -> Unit
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

    fun offButtons(){
        simonYellowBtn.viewDeactivate()
        simonBlueBtn.viewDeactivate()
        simonRedBtn.viewDeactivate()
        simonGreenBtn.viewDeactivate()
    }

    fun newLevel(){
        val randomNumber = Random.nextInt(1, 5)
        this.moves.add(randomNumber)

        this.currentPlay = 0
        lblScore.text = score.toString()

        lifecycleOwner.lifecycleScope.launch {
            disableButtons()

            for (move in moves) {
                delay(1500)
                when (move) {
                    1 -> simonYellowBtn.activate(this@SimonGame)
                    2 -> simonBlueBtn.activate(this@SimonGame)
                    3 -> simonRedBtn.activate(this@SimonGame)
                    4 -> simonGreenBtn.activate(this@SimonGame)
                }
            }
            delay(1500)
            enableButtons()
        }
    }

    fun check(selection: Int){
        if (selection == moves[currentPlay]){
            currentPlay++
            if (currentPlay == moves.size){
                score++
                newLevel()
            }
            lifecycleOwner.lifecycleScope.launch {
                delay(1100)
                enableButtons()
            }
        }else{
            onGameEnd(this.score)
        }
    }
}
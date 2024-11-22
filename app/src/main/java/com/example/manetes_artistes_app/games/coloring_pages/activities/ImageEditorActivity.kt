package com.example.manetes_artistes_app.games.coloring_pages.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import com.example.manetes_artistes_app.R
import com.example.manetes_artistes_app.games.coloring_pages.colors.ColorLoader
import com.example.manetes_artistes_app.games.coloring_pages.colors.ColorPalette
import com.example.manetes_artistes_app.common.ActivitiesIntentKeys
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity
import com.example.manetes_artistes_app.games.coloring_pages.files.BitmapStorageHelper
import com.example.manetes_artistes_app.games.coloring_pages.stats.DrawStat
import com.example.manetes_artistes_app.games.coloring_pages.stats.StatsState
import com.example.manetes_artistes_app.imageEditor.CanvasView
import com.example.manetes_artistes_app.imageEditor.Draw
import com.example.manetes_artistes_app.menus.MainMenuActivity
import com.example.manetes_artistes_app.user.User


class ImageEditorActivity: ImmersiveCompatActivity() {

    private var selectedColor: Int = Color.parseColor("#f59542")
    private var canvas: CanvasView? = null
    private var bitmap: Bitmap? = null
    private var user: User = User(0, 0, 0)
    @SuppressLint("ClickableViewAccessibility")
    private var drawStat: DrawStat = DrawStat(
        0,
        (System.currentTimeMillis() / 1000).toInt(),
        0,
        0,
        0
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_editor)
        user = User.extractUserFromIntent(intent)
        try {
            val drawData: Draw? = intent.getSerializableExtra(ActivitiesIntentKeys.DRAW_DATA) as Draw?
            drawStat.draw_id = drawData!!.id
            if (drawData != null) {
                renderDrawWhiteImage(drawData.whiteImage, drawData)
                renderBackgroundImage(drawData.backgroundImage)
                renderColorPalette(drawData)
            }
            addOnSaveClickListener(drawData)
        }catch (e: Exception) {
            Log.e("Error", e.toString())
            Log.e("Error", e.stackTraceToString())
        }

    }

    private fun renderBackgroundImage(resourceString: String){
        val resource = resources.getIdentifier(resourceString, "drawable", packageName)
        val containerImageView = findViewById<LinearLayout>(R.id.containerImageView)
        containerImageView.setBackgroundResource(resource)
    }

    private fun renderDrawWhiteImage(resourceString: String, draw: Draw){
        val resource = resources.getIdentifier(draw.whiteImage, "drawable", packageName)
        val defaultBitmap = BitmapFactory.decodeResource(resources, resource)
        bitmap = BitmapStorageHelper(this).loadUserBitmap(user,draw, defaultBitmap)

        canvas = findViewById(R.id.canvasView)

        canvas?.initCanvas(bitmap!!) {
            bitmap = it
        }
    }



    private fun renderColorPalette(draw: Draw){
        val colorPalette = findViewById<ColorPalette>(R.id.colorPalette)
        val colors = ColorLoader.getColorsByIds(draw.colors, this)

        colorPalette.setColors(colors)
         setSelectedColor(Color.parseColor(colors[0].hex))
        // Set the listener to handle color selection
        colorPalette.onColorSelected = { selectedColor ->
            this.setSelectedColor(selectedColor)
            println(this.selectedColor)
        }

        val resource = resources.getIdentifier(draw.coloredImage, "drawable", packageName)
        val bgResource = resources.getIdentifier(draw.squareBackgroundImage, "drawable", packageName)
        colorPalette.renderColorImage(resource, bgResource)
    }

    private fun addOnSaveClickListener(drawData: Draw?){
        val doneButton = findViewById<ImageButton>(R.id.doneButton)

        doneButton.setOnClickListener {
            if(drawData != null){
                val bitmapStorage = BitmapStorageHelper(this)
                bitmapStorage.storeUserBitmap(user!!, bitmap!!, drawData)
                println("Image saved")
            }else{
                println("Error saving image")
            }

            // Save draw stat
            drawStat.endTimestamp = (System.currentTimeMillis() / 1000).toInt()
            drawStat.durationSeconds = drawStat.startTimestamp - drawStat.endTimestamp
            // Store the draw stat
            StatsState.addStat(drawStat,  user!!, this)

            // return to list image activity
            val intent = Intent(this, ImageListActivity::class.java)
            intent.putExtra(ActivitiesIntentKeys.USER, user)
            startActivity(intent)
        }
    }

    fun setSelectedColor(color: Int) {
        selectedColor = color
        canvas?.setFillColor(selectedColor)
        drawStat.usedColorsAmount += 1
    }
}
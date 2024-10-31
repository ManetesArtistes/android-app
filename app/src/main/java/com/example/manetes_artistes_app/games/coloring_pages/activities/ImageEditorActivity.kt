package com.example.manetesartistes_game.activities

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.example.manetesartistes_game.R
import com.example.manetesartistes_game.colors.ColorLoader
import com.example.manetesartistes_game.colors.ColorPalette
import com.example.manetesartistes_game.common.ActivitiesIntentKeys
import com.example.manetesartistes_game.imageEditor.CanvasView
import com.example.manetesartistes_game.imageEditor.Draw

class ImageEditorActivity: AppCompatActivity() {

    private var selectedColor: Int = Color.parseColor("#f59542")
    private var canvas: CanvasView? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION       // Hides the navigation bar
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  // Allows layout to expand under navigation
                        or View.SYSTEM_UI_FLAG_FULLSCREEN          // Hides the status bar
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN   // Allows layout to expand under the status bar
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY    // Ensures that the UI stays hidden even after user interaction
                )
        setContentView(R.layout.activity_image_editor)

        try {
            val drawData: Draw? = intent.getSerializableExtra(ActivitiesIntentKeys.DRAW_DATA) as Draw?

            if (drawData != null) {
                renderDrawWhiteImage(drawData.whiteImage)
                renderBackgroundImage(drawData.backgroundImage)
                renderColorPalette(drawData)
            }

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

    private fun renderDrawWhiteImage(resourceString: String){
        val resource = resources.getIdentifier(resourceString, "drawable", packageName)
        canvas = findViewById(R.id.canvasView)
        canvas?.initCanvas(BitmapFactory.decodeResource(resources, resource))
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

    fun setSelectedColor(color: Int) {
        selectedColor = color
        canvas?.setFillColor(selectedColor)
    }

}
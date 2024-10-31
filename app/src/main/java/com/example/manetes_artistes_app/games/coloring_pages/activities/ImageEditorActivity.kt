package com.example.manetes_artistes_app.games.coloring_pages.activities

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.example.manetes_artistes_app.R
import com.example.manetes_artistes_app.games.coloring_pages.colors.ColorLoader
import com.example.manetes_artistes_app.games.coloring_pages.colors.ColorPalette
import com.example.manetes_artistes_app.common.ActivitiesIntentKeys
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity
import com.example.manetes_artistes_app.imageEditor.CanvasView
import com.example.manetes_artistes_app.imageEditor.Draw

class ImageEditorActivity: ImmersiveCompatActivity() {

    private var selectedColor: Int = Color.parseColor("#f59542")
    private var canvas: CanvasView? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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
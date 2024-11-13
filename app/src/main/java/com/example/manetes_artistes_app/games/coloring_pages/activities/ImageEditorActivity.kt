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
import com.example.manetes_artistes_app.imageEditor.CanvasView
import com.example.manetes_artistes_app.imageEditor.Draw
import com.example.manetes_artistes_app.menus.MainMenuActivity


class ImageEditorActivity: ImmersiveCompatActivity() {

    private var selectedColor: Int = Color.parseColor("#f59542")
    private var canvas: CanvasView? = null
    private var bitmap: Bitmap? = null
    @SuppressLint("ClickableViewAccessibility")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_editor)

        try {
            val drawData: Draw? = intent.getSerializableExtra(ActivitiesIntentKeys.DRAW_DATA) as Draw?

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

    private fun renderDrawWhiteImage(resourceString: String, drawData: Draw?){
        val resource = resources.getIdentifier(resourceString, "drawable", packageName)
        canvas = findViewById(R.id.canvasView)
        var initialBitmap: Bitmap? = null;
        val loadExistingBitmap = BitmapStorageHelper(this).loadBitmapById(drawData?.id.toString())
        val defaultBitmap = BitmapFactory.decodeResource(resources, resource)
        if(loadExistingBitmap != null){
            println("load existing bitmap")
            initialBitmap = loadExistingBitmap
        }else{
            println("load default bitmap")
            initialBitmap = defaultBitmap
        }
        this.bitmap = initialBitmap

        canvas?.initCanvas(initialBitmap!!) {
            this.bitmap = it
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
                bitmapStorage.saveBitmapAsPng(bitmap!!, drawData?.id.toString())
                println("Image saved")
            }else{
                println("Error saving image")
            }

            val intent = Intent(this, ImageListActivity::class.java)
            startActivity(intent)
        }
    }

    fun setSelectedColor(color: Int) {
        selectedColor = color
        canvas?.setFillColor(selectedColor)
    }
}
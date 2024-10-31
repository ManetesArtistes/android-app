package com.example.manetesartistes_game.colors

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manetesartistes_game.R

class ColorPalette @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val colorPaletteRecyclerView: RecyclerView
    private var colors: List<Color> = listOf()

    init {
        // Inflate the layout for this custom view
        LayoutInflater.from(context).inflate(R.layout.view_color_palette, this, true)

        // Initialize the RecyclerView
        colorPaletteRecyclerView = findViewById(R.id.colorPaletteRecyclerView)
        setupRecyclerView()
    }

    fun setColors(c: List<Color>) {
        this.colors = c
        render()
    }

    private fun setupRecyclerView() {
        // Se configura el GridLayoutManager con 2 columnas
        val layoutManager = GridLayoutManager(context, 2)
        colorPaletteRecyclerView.layoutManager = layoutManager
        render()
    }

    private fun render() {
        val adapter = ColorAdapter(context, this.colors) { selectedColor ->
            onColorSelected(selectedColor)
        }
        colorPaletteRecyclerView.adapter = adapter
    }

     fun renderColorImage(image: Int, bgImage: Int) {

        if(image!=0){
            val imageView = findViewById<ImageView>(R.id.colorPaletteImage)
            imageView.setImageResource(image)
        }

         if(bgImage!=0){
             val containerImageView = findViewById<LinearLayout>(R.id.bgColorPaletteImage)
             containerImageView.setBackgroundResource(bgImage)
         }

    }

    // defined at image editor renderColorPalette
    var onColorSelected: (Int) -> Unit = {}
}
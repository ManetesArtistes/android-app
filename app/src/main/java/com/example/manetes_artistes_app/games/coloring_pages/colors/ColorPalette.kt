package com.example.manetes_artistes_app.games.coloring_pages.colors

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manetes_artistes_app.games.coloring_pages.colors.Color
import com.example.manetes_artistes_app.games.coloring_pages.colors.ColorAdapter
import com.example.manetes_artistes_app.R

class ColorPalette @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val colorPaletteRecyclerView: RecyclerView
    private var colors: List<Color> = listOf()

    init {
        LayoutInflater.from(context).inflate(R.layout.view_color_palette, this, true)
        colorPaletteRecyclerView = findViewById(R.id.colorPaletteRecyclerView)
        setupRecyclerView()
    }

    fun setColors(c: List<Color>) {
        this.colors = c
        render()
    }

    private fun setupRecyclerView() {
        val layoutManager = object : GridLayoutManager(context, 2){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
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

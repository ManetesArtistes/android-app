package com.example.manetes_artistes_app.games.coloring_pages.colors

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.example.manetes_artistes_app.R

class ColorAdapter(
    private val context: Context,
    private val colors: List<Color>,
    private val onColorSelected: (Int) -> Unit
) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    private var selectedColor: ImageView = ImageView(context)

    inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val colorImageView: ImageView = itemView.findViewById(R.id.colorImage)

        fun bind(color: Color, position: Int) {
            val imageResId = context.resources.getIdentifier(color.image, "drawable", context.packageName)

            if (imageResId == 0) {
                println("Image not found: ${color.image}")
            }
            colorImageView.setImageResource(imageResId)

            if (position == 0) {
                colorImageView.setPadding(0) // Seleccionat
                selectedColor = colorImageView
            } else {
                colorImageView.setPadding(10) // No seleccionat
            }

            val layoutParams = itemView.layoutParams as ViewGroup.MarginLayoutParams

            when (position) {
                0,2,4,6 ->  {
                    layoutParams.topMargin = context.resources.getDimensionPixelSize(R.dimen.top_margin_firstcol)
                    layoutParams.bottomMargin = 0
                    itemView.layoutParams = layoutParams
                }
                else -> {
                    layoutParams.topMargin = 0
                    layoutParams.bottomMargin = context.resources.getDimensionPixelSize(R.dimen.top_margin_firstcol)
                    itemView.layoutParams = layoutParams
                }
            }

            itemView.setOnClickListener {
                reproduceColorSound()
                onColorSelected(android.graphics.Color.parseColor(color.hex))
                selectedColor.setPadding(10)
                selectedColor = colorImageView
                selectedColor.setPadding(0)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.color_item, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colors[position]
        holder.bind(color, position)
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    private fun reproduceColorSound(){
        val sound: MediaPlayer = MediaPlayer.create(context, R.raw.color)
        sound.start()
        sound.setOnCompletionListener {
            sound.release()
        }
    }
}

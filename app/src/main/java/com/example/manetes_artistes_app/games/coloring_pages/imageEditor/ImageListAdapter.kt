package com.example.manetes_artistes_app.imageEditor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.manetes_artistes_app.R

class ImageListAdapter(
    private val context: Context,
    private val draws: List<Draw>,
    private val onDrawTouch: (Draw) -> Unit
) : RecyclerView.Adapter<ImageListAdapter.DrawViewHolder>() {

    inner class DrawViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val whiteImageView: ImageView = itemView.findViewById(R.id.whiteImageView)
        private val backgroundContainer: LinearLayout = itemView.findViewById(R.id.backgroundContainer)

        fun bind(draw: Draw) {
            val whiteImageResId = context.resources.getIdentifier(draw.whiteImage, "drawable", context.packageName)
            if (whiteImageResId != 0) {
                whiteImageView.setImageResource(whiteImageResId)
            } else {
                println("White image not found: ${draw.whiteImage}")
            }

            val backgroundImageResId = context.resources.getIdentifier(draw.backgroundImage, "drawable", context.packageName)
            if (backgroundImageResId != 0) {
                backgroundContainer.setBackgroundResource(backgroundImageResId)
            } else {
                println("Background image not found: ${draw.backgroundImage}")
            }

            backgroundContainer.setOnClickListener {
                onDrawTouch(draw)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_draw, parent, false)
        return DrawViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrawViewHolder, position: Int) {
        val draw = draws[position]
        holder.bind(draw)
    }

    override fun getItemCount(): Int {
        return draws.size
    }
}

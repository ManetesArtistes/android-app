package com.example.manetes_artistes_app.games.coloring_pages.imageEditor

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.manetes_artistes_app.R
import com.example.manetes_artistes_app.games.coloring_pages.files.BitmapStorageHelper
import com.example.manetes_artistes_app.games.coloring_pages.files.FtpClient
import com.example.manetes_artistes_app.imageEditor.Draw
import com.example.manetes_artistes_app.user.User

class ImageListAdapter(
    private val context: Context,
    private val draws: List<Draw>,
    private val user: User,
    private val resources: Resources,
    private val packageName: String,
    private val onDrawTouch: (Draw) -> Unit
) : RecyclerView.Adapter<ImageListAdapter.DrawViewHolder>() {

    inner class DrawViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val whiteImageView: ImageView = itemView.findViewById(R.id.whiteImageView)
        private val backgroundContainer: LinearLayout = itemView.findViewById(R.id.backgroundContainer)

        fun bind(draw: Draw) {
            val resource = resources.getIdentifier(draw.whiteImage, "drawable", packageName)
            val defaultBitmap = BitmapFactory.decodeResource(resources, resource)
            val bitmap: Bitmap = BitmapStorageHelper(context).loadUserBitmap(user,draw, defaultBitmap)
            whiteImageView.setImageBitmap(bitmap)

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

package com.example.manetes_artistes_app.menus.stickers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.manetes_artistes_app.R

class StickerAdapter(
    private val context: Context,
    private val stickers: List<Sticker>,
    private val onStickerTouch: (Sticker) -> Unit
) : RecyclerView.Adapter<StickerAdapter.StickerViewHolder>() {

    inner class StickerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgSticker: ImageButton = view.findViewById(R.id.stickerButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sticker_button, parent, false)
        return StickerViewHolder(view)
    }

    override fun onBindViewHolder(holder: StickerViewHolder, position: Int) {
        val sticker = stickers[position]
        val backgroundImageResId = context.resources.getIdentifier(sticker.image, "drawable", context.packageName)
        if (backgroundImageResId != 0) {
            holder.imgSticker.setBackgroundResource(backgroundImageResId)
            holder.imgSticker.setOnClickListener {
                onStickerTouch(sticker)
            }
        } else {
            println("Background image not found: ${sticker.image}")
        }
    }

    override fun getItemCount(): Int {
        return stickers.size
    }
}
package com.example.manetes_artistes_app.menus.activities

import com.example.manetes_artistes_app.menus.stickers.Sticker
import com.example.manetes_artistes_app.menus.stickers.StickerAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manetes_artistes_app.R
import com.example.manetes_artistes_app.common.ActivitiesIntentKeys
import com.example.manetes_artistes_app.common.ImmersiveCompatActivity
import com.example.manetes_artistes_app.menus.stickers.StickerLoader
import com.example.manetes_artistes_app.user.User

class StickerSelectorActivity : ImmersiveCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sticker_selector)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val listView = findViewById<RecyclerView>(R.id.animalSelectorView)
        val layoutManager = object : GridLayoutManager(this, 4){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        val stickers = StickerLoader.getAllStickers(this)

        listView.layoutManager = layoutManager
        listView.adapter = StickerAdapter(this, stickers)
        { selectedSticker ->
            onStickerSelected(selectedSticker)
        }
    }

    private fun onStickerSelected(sticker: Sticker) {
        val intent = Intent(this, MainMenuActivity::class.java)
        StickerLoader.loadStickers(this)
        intent.putExtra(ActivitiesIntentKeys.STICKER, sticker)
        startActivity(intent)
        User.stickerId = sticker.id
    }
}
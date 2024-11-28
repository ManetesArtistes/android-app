package com.example.manetes_artistes_app.menus.stickers

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Sticker(
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: String,
): Serializable
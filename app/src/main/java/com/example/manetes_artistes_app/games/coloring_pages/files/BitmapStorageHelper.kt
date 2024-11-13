package com.example.manetes_artistes_app.games.coloring_pages.files

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class BitmapStorageHelper(private val context: Context) {

    // Guarda el Bitmap como PNG con el ID dado
    fun saveBitmapAsPng(bitmap: Bitmap, id: String): Boolean {
        return try {
            // Ruta para almacenar la imagen
            val file = File(context.filesDir, "$id.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    // Lee la imagen almacenada y la convierte nuevamente en un Bitmap
    fun loadBitmapById(id: String): Bitmap? {
        return try {
            val file = File(context.filesDir, "$id.png")
            if (file.exists()) {
                val inputStream = FileInputStream(file)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()
                bitmap
            } else {
                null // Si no existe el archivo, devuelve null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}

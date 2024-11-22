package com.example.manetes_artistes_app.common.files

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.manetes_artistes_app.imageEditor.Draw
import com.example.manetes_artistes_app.user.User
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class BitmapStorageHelper(private val context: Context) {

    fun storeUserBitmap(bitmap: Bitmap, draw: Draw): Boolean {
        val id = generateId( draw)
        println("Image stored at id: $id")
        return saveBitmapAsPng(bitmap, id)
    }

    fun loadUserBitmap(draw: Draw, defaultBitmap: Bitmap): Bitmap {
        val id = generateId(draw)
        println("Image loaded from id: $id")

        var userBitmap: Bitmap? = loadBitmapById(id)

        if(userBitmap == null){
            userBitmap = defaultBitmap
        }
        return userBitmap
    }

    private fun loadDeaultBitmap(draw: Draw): Bitmap? {
        return try {
            val inputStream = context.assets.open(draw.whiteImage)
            BitmapFactory.decodeStream(inputStream )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun generateId(draw: Draw): String {
        return "cid_${User.centerId}_gid_${User.groupId}_sid_${User.stickerId}_drawid_${draw.id}"
    }

    private fun saveBitmapAsPng(bitmap: Bitmap, id: String): Boolean {
        return try {
            // Ruta para almacenar la imagen
            val file = File(context.filesDir, "$id.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            Thread {
                // save on ftp
                val ftpClient = FtpClient()
                ftpClient.uploadBitmap(bitmap, context, "$id.png")
            }.start()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun loadBitmapById(id: String): Bitmap? {
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

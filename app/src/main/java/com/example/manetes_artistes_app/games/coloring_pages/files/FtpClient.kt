package com.example.manetes_artistes_app.games.coloring_pages.files

import android.content.Context
import android.graphics.Bitmap
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class FtpClient() {
    private val ftpClient = FTPClient()

    val host = "ftp.onwindows-es.setupdns.net"
    val user = "dam_03@abp-politecnics.com"
    val password = "Politecnics2025"

    private fun connect() {
        try {
            ftpClient.connect(host)
            val login = ftpClient.login(user, password)
            if (!login) {
                println("FTP login failed")
            } else {
                println("FTP login successful")
                ftpClient.enterLocalPassiveMode() // Set passive mode
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE) // Set file type to binary
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun disconnect() {
        try {
            ftpClient.logout()
            ftpClient.disconnect()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Convert Bitmap to PNG and save as a file
    private fun saveBitmapToFile(bitmap: Bitmap, context: Context, filename: String): File? {
        val file = File(context.cacheDir, filename)
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            return file
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    // Upload file to FTP server
    fun uploadFileToFtp(file: File) {
        try {
            val inputStream: InputStream = file.inputStream()
            val result = ftpClient.storeFile("/images/${file.name}", inputStream)
            inputStream.close()
            if (result) {
                println("File uploaded successfully")
            } else {
                println("File upload failed")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Full process to save Bitmap, convert to PNG, and upload to FTP
    fun uploadBitmap(bitmap: Bitmap, context: Context, filename: String) {
        Thread {
            try {
                val file = saveBitmapToFile(bitmap, context, filename)
                if (file != null) {
                    connect()
                    uploadFileToFtp(file)
                    disconnect()
                } else {
                    println("Failed to save the bitmap as PNG")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }
}
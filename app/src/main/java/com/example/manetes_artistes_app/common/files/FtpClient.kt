package com.example.manetes_artistes_app.common.files

import android.content.Context
import android.graphics.Bitmap
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class FtpClient {
    private val ftpClient = FTPClient()

    val host = "ftp.onwindows-es.setupdns.net"
    val user = "dam_03@abp-politecnics.com"
    val password = "Politecnics2025"

    // Connect to the FTP server
    fun connect() {
        try {
            if (!ftpClient.isConnected) {
                ftpClient.connect(host)
                val login = ftpClient.login(user, password)
                if (!login) {
                    println("FTP login failed")
                    disconnect() // Ensure clean disconnect on failure
                } else {
                    println("FTP login successful")
                    ftpClient.enterLocalPassiveMode() // Set passive mode
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE) // Set file type to binary
                }
            } else {
                println("Already connected to the FTP server")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Disconnect from the FTP server
    fun disconnect() {
        try {
            if (ftpClient.isConnected) {
                ftpClient.logout()
                ftpClient.disconnect()
                println("Disconnected from FTP server")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Upload file to FTP server
    fun uploadFileToFtp(file: File, path: String) {
        Thread{
            try {
                if (!ftpClient.isConnected) {
                    println("Not connected to FTP. Attempting to reconnect...")
                    connect()
                }

                val inputStream: InputStream = file.inputStream()
                val result = ftpClient.storeFile("${path}${file.name}", inputStream)
                inputStream.close()
                if (result) {
                    println("File uploaded successfully at path ${path}${file.name}")
                } else {
                    println("File upload failed at path ${path}${file.name}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    // Full process to save Bitmap, convert to PNG, and upload to FTP
    fun uploadBitmap(bitmap: Bitmap, context: Context, filename: String) {
        Thread {
            try {
                connect() // Ensure connection before the upload process
                val file = saveBitmapToFile(bitmap, context, filename)
                if (file != null) {
                    uploadFileToFtp(file, "/images/")
                } else {
                    println("Failed to save the bitmap as PNG")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
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
}


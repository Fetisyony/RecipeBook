package com.example.recipebook.data.source.remote

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.recipebook.data.source.remote.RecipeApiService.Companion.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


private fun extractNameFromUrl(url: String): String {
    val lastSlashIndex = url.lastIndexOf('/')

    return if (lastSlashIndex != -1) {
        url.substring(lastSlashIndex + 1)
    } else {
        url
    }
}
fun downloadImageInner(context: Context, imageUrl: String): Uri? {
    val url = URL(BASE_URL + imageUrl)
    val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
    connection.doInput = true
    connection.connect()
    val input: InputStream = connection.inputStream
    val bitmap: Bitmap = BitmapFactory.decodeStream(input)

    val file = File(context.filesDir, extractNameFromUrl(imageUrl))
    val outputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.flush()
    outputStream.close()

    return Uri.fromFile(file)
}

suspend fun downloadImage(context: Context, imageUrl: String): Uri? {
    return withContext(Dispatchers.IO) {
        try {
            downloadImageInner(context, imageUrl)
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Toast
                    .makeText(
                        context,
                        "Something went wrong while loading an image",
                        Toast.LENGTH_LONG
                    )
                    .show()
            }
            null
        }
    }
}

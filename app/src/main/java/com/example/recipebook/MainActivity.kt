package com.example.recipebook

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.recipebook.ui.screens.AddRecipeDialog
import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.recipebook.ui.screens.App
import com.example.recipebook.ui.components.UploadStatus
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : ComponentActivity() {
    var chosenImageUri: Uri? = null
    var uploadStatus: UploadStatus? = null
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            chosenImageUri = uri
            uploadStatus?.setNewUri(chosenImageUri!!)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {}
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        setContent {
            App { a, b ->
                AddRecipeDialog(
                    { status -> pickImageDialog(status) },
                    onDismissRequest = { b() },
                    onSave = { c, d ->
                        val file = copyImageToInternalStorage(d)
                        if (file != null) {
                            c(file)
                        }
                    },
                    a
                )
            }
        }
    }

    private fun pickImageDialog(status: UploadStatus) {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        uploadStatus = status
    }

    private fun copyImageToInternalStorage(dismiss: () -> Unit): Uri? {
        if (chosenImageUri == null) {
            Log.d("TEST", "Nothing to copy, image was not chosen")
            return null
        }
        dismiss()

        return try {
            // creates a file in the app's internal storage
            val inputStream: InputStream? = contentResolver.openInputStream(chosenImageUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            Log.d("TEST", "${ chosenImageUri!!.lastPathSegment }")
            val file = File(filesDir, "${chosenImageUri!!.lastPathSegment}.jpg")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

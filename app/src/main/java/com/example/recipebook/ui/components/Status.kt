package com.example.recipebook.ui.components

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


class UploadStatus {
    private val uploadSuggestionStatus = "Upload image"
    private val uploadedStatus = "Uploaded!"

    private var uri: Uri? by mutableStateOf(null)
    var isUploaded by mutableStateOf(false)

    fun setNewUri(newUri: Uri) {
        uri = newUri
        isUploaded = true
    }

    fun getCurrentUri(): Uri? {
        return uri
    }

    fun getCurrent(): String {
        if (isUploaded)
            return uploadedStatus
        return uploadSuggestionStatus
    }
}

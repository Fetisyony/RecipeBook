package com.example.recipebook.ui.screens

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.recipebook.R
import com.example.recipebook.data.model.Recipe
import com.example.recipebook.data.model.RecipeId
import com.example.recipebook.ui.components.UploadStatus
import com.example.recipebook.ui.theme.DialogLightBlue


@Composable
fun AddRecipeDialog(
    uploadImage: (UploadStatus) -> Unit,
    onDismissRequest: () -> Unit,
    onSave: (addRecipe: (Uri) -> Unit, onDismissRequest: () -> Unit) -> Unit,
    addRecipe: (Recipe) -> Unit
) {
    var dishName by remember { mutableStateOf("") }
    var dishDesc by remember { mutableStateOf("") }
    val status = remember { UploadStatus() }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(shape = RoundedCornerShape(8.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                UploadButtonIcon(uploadImage, status)
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = dishName,
                    onValueChange = {
                        if (it.length < 25)
                            dishName = it
                                    },
                    label = { Text(text = stringResource(R.string.recipe_name_entry_hint)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = dishDesc,
                    onValueChange = {
                        if (it.length < 50)
                            dishDesc = it
                                    },
                    label = { Text(text = stringResource(R.string.recipe_descr_entry_hint)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    DialogButton("Cancel", onDismissRequest)
                    Spacer(modifier = Modifier.width(8.dp))
                    DialogButton("Save") {
                        onSave(
                            { c -> addRecipe(Recipe(RecipeId.id++, dishName, dishDesc, c)) },
                            { onDismissRequest() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UploadButtonIcon(uploadImage: (UploadStatus) -> Unit, status: UploadStatus) {
    Button(
        onClick = { uploadImage(status) },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var painter: Painter = painterResource(id = R.drawable.outline_upload_file_24)
            var colorTint: ColorFilter? = ColorFilter.tint(DialogLightBlue)
            if (status.isUploaded) {
                val uri = status.getCurrentUri()
                painter = rememberAsyncImagePainter(model=uri)
                colorTint = null
            }
            Image(
                painter = painter,
                colorFilter = colorTint,
                contentDescription = "Upload Image Button Icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(60.dp)
                    .size(65.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = status.getCurrent(),
                fontSize = 15.sp,
                color = DialogLightBlue,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun DialogButton(text: String, onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = DialogLightBlue,
            contentColor = Color.White
        ),
        modifier = Modifier.width(100.dp),
        onClick = onClick
    ) {
        Text(text)
    }
}

@Preview
@Composable
fun AddRecipeDialogPreview() {
    AddRecipeDialog(
        {},
        onDismissRequest = {},
        onSave = { _, _ -> },
        {}
    )
}
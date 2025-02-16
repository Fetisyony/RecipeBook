package com.example.recipebook.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.recipebook.R
import com.example.recipebook.data.model.Recipe
import com.example.recipebook.data.model.recipes
import com.example.recipebook.ui.theme.CustomTextStyle


@Composable
fun RecipeCard(recipe: Recipe) {
    val context = LocalContext.current
    Column {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
            colors = CardColors(
                contentColor = Color.White,
                containerColor = Color.White,
                disabledContentColor = Color.White,
                disabledContainerColor = Color.White
            ),
            modifier = Modifier
                .height(90.dp)
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .clickable {
                    Toast
                        .makeText(
                            context,
                            "Not implemented yet",
                            Toast.LENGTH_LONG
                        )
                        .show()
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 17.dp)
            ) {
                var painter = painterResource(id = R.drawable.default_img)
                if (recipe.imageUri != null)
                    painter = rememberAsyncImagePainter(model = recipe.imageUri)

                Image(
                    painter = painter,
                    contentDescription = "Recipe picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(60.dp)
                        .size(65.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(15.dp))
                RecipeDescription(recipe)
            }
        }
    }
}

@Composable
fun RecipeDescription(recipe: Recipe) {
    Column {
        Row {
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = recipe.name,
                style = CustomTextStyle.recipeTitleStyle,
                modifier = Modifier
                    .padding(top = 1.dp, bottom = 5.dp)
            )
        }

        Text(
            text = recipe.description,
            style = CustomTextStyle.recipeDescriptionStyle
        )
    }
}

@Preview
@Composable
fun RecipeCardPreview() {
    RecipeCard(recipes[0])
}

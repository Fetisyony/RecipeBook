package com.example.recipebook.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object CustomTextStyle {
    val pageTitleStyle = TextStyle(
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
    val recipeTitleStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        color = Color.Black
    )
    val recipeDescriptionStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Color.Gray
    )
    val bottomBarTitle = TextStyle(
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = ScaffoldFg
    )
}

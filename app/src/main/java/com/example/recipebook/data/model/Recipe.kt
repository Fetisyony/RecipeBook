package com.example.recipebook.data.model

import android.net.Uri
import com.example.recipebook.R

data class Recipe(val id: Int, val name: String, val description: String, val imageUri: Uri?, val imageUrl: String? = null)

data class TechRecipe(
    val id: Int, val name: String, val description: String, val imageUrl: String
)

fun getImageUri(resourceId: Int): Uri {
    return Uri.parse("android.resource://com.example.recipebook/$resourceId")
}

var recipes = listOf(
    Recipe(1,"Apple Pie", "A nice apple pie that can fit any evening you have!", null), // R.drawable.apple_pie
    Recipe(2, "Coq au Vin", "Chicken braised in red wine with mushrooms and bacon", null), // R.drawable.coq_au_vin
    Recipe(3, "Sushi", "Vinegared rice with fresh fish, vegetables, and seaweed", null), // R.drawable.sushi
    Recipe(4, "Paella", "Saffron rice with seafood, meats, and vegetables", null), // R.drawable.paella
    Recipe(5, "Ratatouille", "Stewed vegetables with herbs", null), // R.drawable.ratatouille
    Recipe(6, "Tandoori Chicken", "Spiced chicken grilled in a tandoor", null), // R.drawable.tandoori_chicken
    Recipe(7, "Beef Wellington", "Beef tenderloin wrapped in puff pastry with mushrooms", null), // R.drawable.beef_wellington
    Recipe(8, "Tom Yum Goong", "Thai spicy and sour shrimp soup", null), // R.drawable.tom_yum_goong
    Recipe(9, "Boeuf Bourguignon", "Beef stew braised in red wine with vegetables", null), // R.drawable.boeuf_bourguignon
    Recipe(10, "Peking Duck", "Crispy duck served with pancakes and hoisin sauce", null), // R.drawable.peking_duck
    Recipe(11, "Caprese Salad", "Fresh tomatoes, mozzarella, and basil with olive oil", null) // R.drawable.caprese_salad
)

object RecipeId {
    var id: Int = recipes.size + 1
}

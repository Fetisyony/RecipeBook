package com.example.recipebook.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.data.model.Recipe
import com.example.recipebook.data.repository.DatabaseProvider
import com.example.recipebook.data.repository.RecipeRepository
import com.example.recipebook.ui.components.RecipeCard
import com.example.recipebook.ui.theme.RecipeBookTheme
import com.example.recipebook.ui.theme.ScaffoldBg
import com.example.recipebook.ui.theme.ScaffoldFg
import com.example.recipebook.ui.theme.ScaffoldIndicator
import com.example.recipebook.ui.theme.ScaffoldSelected
import com.example.recipebook.viewmodel.RecipeViewModel

@Composable
fun HomeScreen(items: List<Recipe>, padding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(top = padding.calculateTopPadding(), bottom = padding.calculateBottomPadding())
            .fillMaxSize()
    ) {
        RecipeCardList(items)
    }
}

@Composable
fun FavouritesScreen() {
    Text(
        text = "Favourites not implemented yet",
        fontSize = 20.sp
    )
}

class RecipeViewModelFactory(
    private val repository: RecipeRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(showNewRecipeDialog: @Composable ( (((Recipe) -> Unit), (() -> Unit)) -> Unit )) {
    val database = DatabaseProvider.getDatabase(LocalContext.current)
    val repository = RecipeRepository(database.recipeDao())
    val recipeViewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(repository, LocalContext.current))

    val navController = rememberNavController()

    val items by recipeViewModel.recipeList.collectAsState()

    RecipeBookTheme(darkTheme = false) {
        Scaffold(
            floatingActionButton = {
                AddRecipeButton(showNewRecipeDialog) {  }
            },
            floatingActionButtonPosition = FabPosition.Center,
            bottomBar = {
                BottomNavigationBar(
                    navController = navController,
                )
            },
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "home",
            ) {
                composable("home") {
                    HomeScreen(items = items, padding)
                }
                composable("favourites") {
                    FavouritesScreen()
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        contentColor = ScaffoldSelected,
        containerColor = ScaffoldBg,
        modifier = Modifier
            .height(70.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: "home"

        NavigationBarItem (
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = ScaffoldIndicator,
                selectedIconColor = ScaffoldFg,
                unselectedIconColor = ScaffoldFg,
                selectedTextColor = ScaffoldFg,
                unselectedTextColor = ScaffoldFg
            ),
            icon = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(90.dp)
                ) {
                    Icon(Icons.Default.Home, contentDescription = "Home")
                    Text("Home")
                } },
            selected = currentRoute == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    restoreState = true
                }
            }
        )

        NavigationBarItem (
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = ScaffoldIndicator,
                selectedIconColor = ScaffoldFg,
                unselectedIconColor = ScaffoldFg,
                selectedTextColor = ScaffoldFg,
                unselectedTextColor = ScaffoldFg
            ),
            icon = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(90.dp)
                ) {
                    Icon(Icons.Default.Favorite, contentDescription = "Favourites")
                    Text("Favourites")
                } },
            selected = currentRoute == "favourites",
            onClick = {
                navController.navigate("favourites") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    restoreState = true
                }
            }
        )
    }
}

@Composable
fun RecipeCardList(items: List<Recipe>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 15.dp)
    ) {
        items(items) {
            RecipeCard(it)
        }
    }
}

@Composable
fun AddRecipeButton(showNewRecipeDialog: @Composable ( (((Recipe) -> Unit), () -> Unit) -> Unit), addNewItem: (Recipe) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    FloatingActionButton(
        onClick = { showDialog = true },
        containerColor = ScaffoldBg,
        contentColor = ScaffoldFg,
        elevation = FloatingActionButtonDefaults.elevation(5.dp)
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = "Add")
    }
    if (showDialog) {
        showNewRecipeDialog(
            { addNewItem(it) },
            { showDialog = false }
        )
    }
}

@Preview(device = Devices.PIXEL_4, showSystemUi = false)
@Composable
fun AppPreview() {
    App({ _, _, ->
    })
}

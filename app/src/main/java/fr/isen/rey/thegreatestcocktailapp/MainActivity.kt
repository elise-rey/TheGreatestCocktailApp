package fr.isen.rey.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.rey.thegreatestcocktailapp.screens.CategoriesScreen
import fr.isen.rey.thegreatestcocktailapp.screens.DetailCocktailScreen
import fr.isen.rey.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme
import kotlinx.coroutines.launch

enum class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    Home("A la une", Icons.Default.Home, "home"),
    List("Catégories", Icons.Default.Menu, "list"),
    Fav("Favoris", Icons.Default.Favorite, "fav")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheGreatestCocktailAppTheme {
                val snackBarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()
                val startNavigationItem = NavigationItem.Home
                val currentNavigationItem = remember { mutableStateOf(startNavigationItem) }
//                val current = remember { mutableStateOf("home") }
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(snackBarHostState)
                    },
                    snackbarHost = {
                        SnackbarHost(snackBarHostState)
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationItem.entries.forEach { navigationItem ->
                                NavigationBarItem(
                                    selected = currentNavigationItem.value == navigationItem,
                                    onClick = {
                                        navController.navigate(navigationItem.route)
                                        currentNavigationItem.value = navigationItem
                                    },
                                    label = {
                                        Text(navigationItem.title)
                                    },
                                    icon = {
                                        Icon(navigationItem.icon, contentDescription = "")
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startNavigationItem.route
                    ) {
                        NavigationItem.entries.forEach { navigationItem ->
                            composable(navigationItem.route) {
                                when (navigationItem) {
                                    NavigationItem.Home -> DetailCocktailScreen(Modifier.padding(innerPadding))
                                    NavigationItem.List -> CategoriesScreen(Modifier.padding(innerPadding))
                                    NavigationItem.Fav -> {}
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(snackbarHostState: SnackbarHostState) {
    CenterAlignedTopAppBar(
        title = {
            Text("Random")
        },
        actions = {
            val added = "Ajouté aux favoris"
            val removed = "Retiré des favoris"
//            val context = LocalContext.current

            val snackbarScope = rememberCoroutineScope()
            val isFav = remember { mutableStateOf(false) }
            IconToggleButton(
                isFav.value,
                onCheckedChange = {
                    isFav.value = !isFav.value
                    // TOAST
//                    Toast.makeText(
//                        context,
//                        if (isFav.value) added else removed,
//                        Toast.LENGTH_SHORT
//                    ).show()
                    snackbarScope.launch {
                        snackbarHostState.showSnackbar(if (isFav.value) added else removed)
                    }
                }
            ) {
                Icon(
                    imageVector = if (isFav.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "fav"
                )
            }
        }
    )
}
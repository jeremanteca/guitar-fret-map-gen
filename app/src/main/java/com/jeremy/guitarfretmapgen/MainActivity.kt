package com.jeremy.guitarfretmapgen

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jeremy.guitarfretmapgen.Screens.FavoritasScreen
import com.jeremy.guitarfretmapgen.Screens.FretboardMapApp
import com.jeremy.guitarfretmapgen.ui.theme.GuitarFretMapGenTheme
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jeremy.guitarfretmapgen.Models.FavoritasViewModel

class MainActivity : ComponentActivity() {

    private val favoritasViewModel: FavoritasViewModel by viewModels {
        FavoritasViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.screenPrincipal) {
                composable("${Routes.screenPrincipal}?tuning={tuning}") { backStackEntry ->
                    val tuning = backStackEntry.arguments?.getString("tuning")
                    FretboardMapApp(navController, favoritasViewModel, initialTuning = tuning)
                }
                composable(Routes.screenFavoritas) {
                    FavoritasScreen(favoritasViewModel) { selectedTuning ->
                        navController.navigate(Routes.screenPrincipalWithTuning(selectedTuning))
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        favoritasViewModel.guardarAfinaciones() // Guardar afinaciones al cerrar la app
    }
}

class FavoritasViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritasViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



//
@Preview
@Composable
fun PrincipalPreview() {
    val navController = rememberNavController()
    val favoritasViewModel = FavoritasViewModel() // Instancia temporal para el preview

    FretboardMapApp(navController = navController, favoritasViewModel = favoritasViewModel)
}

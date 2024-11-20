package com.jeremy.guitarfretmapgen.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeremy.guitarfretmapgen.Models.FavoritasViewModel
import com.jeremy.guitarfretmapgen.ui.theme.GuitarFretMapGenTheme





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritasScreen(favoritasViewModel: FavoritasViewModel, onItemSelected: (String) -> Unit) {
    val itemsList = favoritasViewModel.favoritasList

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "AFINACIONES FAVORITAS") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(itemsList) { item ->
                ListItem(
                    item = item,
                    onItemSelected = onItemSelected,
                    onDeleteItem = { favoritasViewModel.eliminarAfinacion(item) }
                )
            }
        }
    }
}

@Composable
fun ListItem(item: String, onItemSelected: (String) -> Unit, onDeleteItem: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.clickable { onItemSelected(item) } // Actualización para selección
                )
            }
            Button(onClick = onDeleteItem) {
                Text(text = "Eliminar")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun FavoritasPreview() {
    val favoritasViewModel = FavoritasViewModel().apply {
        // Agrega algunos elementos de ejemplo para la vista previa
        agregarAfinacion("EADGBE")
        agregarAfinacion("CGCFAD")
        agregarAfinacion("DADGAD")
    }

    GuitarFretMapGenTheme {
        FavoritasScreen(
            favoritasViewModel = favoritasViewModel,
            onItemSelected = { selectedTuning ->
                // Acción simulada para el preview
                println("Afinación seleccionada: $selectedTuning")
            }
        )
    }
}
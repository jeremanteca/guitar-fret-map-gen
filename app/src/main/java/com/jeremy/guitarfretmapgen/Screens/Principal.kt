package com.jeremy.guitarfretmapgen.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jeremy.guitarfretmapgen.Models.FavoritasViewModel
import com.jeremy.guitarfretmapgen.Routes
import com.jeremy.guitarfretmapgen.ui.theme.GuitarFretMapGenTheme
import java.security.Principal
import androidx.compose.material.icons.filled.ArrowDropDown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FretboardMapApp(
    navController: NavController,
    favoritasViewModel: FavoritasViewModel,
    initialTuning: String? = null
) {
    val context = LocalContext.current
    var tuningInput by remember { mutableStateOf(initialTuning ?: "Ingresa una afinación.") }
    var isEditing by remember { mutableStateOf(false) }
    var tuning by remember { mutableStateOf(emptyList<String>()) }
    var fretboard by remember { mutableStateOf(emptyList<List<String>>()) }

    // Nueva función para actualizar tuningInput desde Favoritas
    fun setTuningInput(newTuning: String) {
        tuningInput = newTuning
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Guitar Fretboard Map Generator") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = { BottomBar(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (tuningInput.isNotBlank()) {
                    favoritasViewModel.agregarAfinacion(tuningInput)
                    Toast.makeText(context, "Afinación agregada a Favoritas", Toast.LENGTH_SHORT).show()
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir afinación a Favoritas")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Entrada para la afinación
            BasicTextField(
                value = tuningInput,
                onValueChange = { newTuning -> tuningInput = newTuning },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused && !isEditing) {
                            tuningInput = "" // Limpiar cuando se enfoca
                            isEditing = true
                        }
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para generar el mapa del diapasón
            Button(
                onClick = {
                    val parsedTuning = parseTuning(tuningInput)
                    if (parsedTuning.size == 6 && parsedTuning.all { it.isValidNote() }) {
                        tuning = parsedTuning
                        fretboard = generateFretboard(parsedTuning)
                    } else {
                        Toast.makeText(
                            context,
                            "Por favor, ingrese una afinación válida de 6 notas.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Generar Mapa del Diapasón")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Muestra el diapasón si está generado
            if (fretboard.isNotEmpty()) {
                FretboardMap(fretboard)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de afinaciones recomendadas
            Recomendadas(onTuningSelected = { selectedTuning ->
                tuningInput = selectedTuning // Actualizar el input con la afinación seleccionada
            })
        }
    }
}

@Composable
fun BottomBar(modifier: Modifier = Modifier,
              navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(5.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                navController.navigate(Routes.screenFavoritas)
            },
            modifier = Modifier


                .background(color = MaterialTheme.colorScheme.primary)

        ) {
            Text("Favoritas")
        }
    }

}

@Composable
fun FretboardMap(fretboard: List<List<String>>) {
    val noteColors = mapOf(
        "A" to Color(0xFFEF9A9A),     // Rojo claro
        "A#" to Color(0xFFE57373),    // Rojo más oscuro
        "B" to Color(0xFFFFF176),     // Amarillo claro
        "C" to Color(0xFFFFD54F),     // Amarillo más oscuro
        "C#" to Color(0xFFFFB74D),    // Naranja claro
        "D" to Color(0xFFFF8A65),     // Naranja oscuro
        "D#" to Color(0xFFA1887F),    // Marrón
        "E" to Color(0xFF81C784),     // Verde claro
        "F" to Color(0xFF4DB6AC),     // Turquesa claro
        "F#" to Color(0xFF4FC3F7),    // Azul claro
        "G" to Color(0xFF64B5F6),     // Azul medio
        "G#" to Color(0xFF9575CD)     // Púrpura claro
    )

    // Scroll states for horizontal and vertical scrolling
    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()

    Box(
        modifier = Modifier
//            .fillMaxSize()
            .verticalScroll(verticalScrollState) // Vertical scroll
            .horizontalScroll(horizontalScrollState) // Horizontal scroll
    ) {
        Column {

            // Agregar los indicadores de trastes
            Row {
                // Espacio en blanco para alinear con las cuerdas
                Spacer(modifier = Modifier.size(40.dp))

                // Indicadores de trastes
                for (fret in 1..22) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(4.dp)
                    ) {
                        Text(
                            text = fret.toString(),
                            style = TextStyle(fontSize = 12.sp)
                        )
                    }
                }
            }



            fretboard.forEach { string ->
                Row {
                    string.forEach { note ->
                        val noteColor = noteColors[note] ?: Color.Gray // Color por defecto si la nota no se encuentra en el mapa
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(noteColor)
                                .padding(4.dp)
                        ) {
                            Text(
                                text = note,
                                style = TextStyle(fontSize = 12.sp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// Lista de afinaciones recomendadas
@Composable
fun Recomendadas(onTuningSelected: (String) -> Unit) {
    // Listas de afinaciones
    val recommendedTunings = listOf("EADGBE", "DADGBE", "CGCFAD", "DADDAD", "FACGCE")
    val SY_Tunings = listOf("CGDGCD", "CGGDDD", "CGDGBB", "DDDDAA", "EGDGED")

    // Estados para controlar si las secciones están desplegadas
    var isRecommendedExpanded by remember { mutableStateOf(false) }
    var isSYExpanded by remember { mutableStateOf(false) }

    Column(
        Modifier.padding(16.dp)
    ) {
        // Título y lista desplegable para Recomendadas
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isRecommendedExpanded = !isRecommendedExpanded }
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .padding(8.dp)
        ) {
            Text(
                text = "RECOMENDADAS (Populares)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.primary
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Expandir lista"
            )
        }
        if (isRecommendedExpanded) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(recommendedTunings) { tuning ->
                    Text(
                        text = tuning,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onTuningSelected(tuning) }
                            .padding(8.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Título y lista desplegable para Sonic Youth
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isSYExpanded = !isSYExpanded }
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .padding(8.dp)
        ) {
            Text(
                text = "SONIC YOUTH",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.primary
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Expandir lista"
            )
        }
        if (isSYExpanded) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(SY_Tunings) { tuning ->
                    Text(
                        text = tuning,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onTuningSelected(tuning) }
                            .padding(8.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}



fun parseTuning(tuningInput: String): List<String> {
    val notes = listOf("A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#")
    val tuning = mutableListOf<String>()
    var i = 0

    while (i < tuningInput.length) {
        if (i < tuningInput.length - 1 && tuningInput[i + 1] == '#') {
            // If the next character is '#', combine it with the current character
            tuning.add(tuningInput.substring(i, i + 2))
            i += 2
        } else {
            // Otherwise, it's a single-character note
            tuning.add(tuningInput[i].toString())
            i++
        }
    }

    // Return only valid notes; you could add validation if needed
    return tuning.filter { it in notes }
}

fun generateFretboard(tuning: List<String>): List<List<String>> {
    val notes = listOf("A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#")
    val fretboard = mutableListOf<List<String>>()

    for (string in tuning) {
        val stringFretNotes = mutableListOf<String>()
        val startNoteIndex = notes.indexOf(string)

        if (startNoteIndex != -1) { // Ensure the note is valid
            for (fret in 0 until 23) {
                stringFretNotes.add(notes[(startNoteIndex + fret) % notes.size])
            }
        } else {
            stringFretNotes.add("?") // Placeholder for invalid notes
        }

        fretboard.add(stringFretNotes)
    }

    return fretboard
}


// Extension function to check if a string is a valid musical note
fun String.isValidNote(): Boolean {
    val validNotes = listOf("A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#")
    return this in validNotes
}

//
@Preview
@Composable
fun PrincipalPreview() {
    val navController = rememberNavController()
    val favoritasViewModel = FavoritasViewModel() // Instancia temporal para el preview

    FretboardMapApp(navController = navController, favoritasViewModel = favoritasViewModel)
}

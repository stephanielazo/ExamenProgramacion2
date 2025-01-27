package cl.stephanielazo.stephaniemedidores.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.stephanielazo.stephaniemedidores.R
import cl.stephanielazo.stephaniemedidores.modelo.Medicion
import cl.stephanielazo.stephaniemedidores.viewmodel.MedicionViewModel

@Composable
fun ListadoMedicionesScreen(viewModel: MedicionViewModel) {
    var mediciones by remember { mutableStateOf(listOf<Medicion>()) }

    // Cargar las mediciones al cargar la pantalla
    LaunchedEffect(Unit) {
        mediciones = viewModel.obtenerMediciones()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Acción para agregar nueva medición */ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar medición")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(mediciones) { medicion ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icono según el tipo
                        val iconRes = when (medicion.tipo) {
                            "AGUA" -> R.drawable.agua
                            "LUZ" -> R.drawable.luz
                            "GAS" -> R.drawable.gas
                            else -> null
                        }

                        iconRes?.let {
                            Image(
                                painter = painterResource(id = it),
                                contentDescription = "Icono ${medicion.tipo}",
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(end = 8.dp)
                            )
                        }

                        // Tipo de medición (nombre)
                        Text(
                            text = medicion.tipo,
                            style = TextStyle(color = Color.Black, fontSize = 16.sp),
                            modifier = Modifier.padding(end = 8.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f)) // Espaciado a la izquierda del número

                        // Valor medido (centrado, sin decimales)
                        Text(
                            text = medicion.valor.toInt().toString(), // Mostrar solo el número entero
                            style = TextStyle(color = Color.Black, fontSize = 16.sp)
                        )

                        Spacer(modifier = Modifier.weight(1f)) // Espaciado a la derecha del número

                        // Fecha (alineada a la derecha)
                        Text(
                            text = medicion.fecha,
                            style = TextStyle(color = Color.Gray, fontSize = 14.sp),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                    Divider(
                        color = Color.LightGray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

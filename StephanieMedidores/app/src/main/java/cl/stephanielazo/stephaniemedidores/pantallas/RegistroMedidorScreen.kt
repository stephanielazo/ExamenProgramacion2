package cl.stephanielazo.stephaniemedidores.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.stephanielazo.stephaniemedidores.viewmodel.MedicionViewModel
import cl.stephanielazo.stephaniemedidores.modelo.Medicion
import kotlinx.coroutines.launch

@Composable
fun RegistroMedidorScreen(navController: NavController, viewModel: MedicionViewModel) {
    // Estados para los campos de entrada
    var numeroMedicion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var tipoMedidor by remember { mutableStateOf("Agua") }

    // Estilo para las etiquetas
    val labelStyle = TextStyle(color = Color.Black)

    // Lista mutable para las mediciones
    val mediciones = remember { mutableStateListOf<Medicion>() }

    // Función para recuperar las mediciones
    val scope = rememberCoroutineScope()
    LaunchedEffect(true) {
        // Cargar las mediciones desde la base de datos
        val medicionesList = viewModel.obtenerMediciones()
        mediciones.clear()
        mediciones.addAll(medicionesList)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registro Medidor",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo para el número de medición
        TextField(
            value = numeroMedicion,
            onValueChange = { numeroMedicion = it },
            label = { Text("Medidor", style = labelStyle) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para la fecha
        TextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha", style = labelStyle) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Selector de tipo de medidor
        Text("Medidor de:")

        // Radio buttons para seleccionar tipo de medidor
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = tipoMedidor == "Agua",
                    onClick = { tipoMedidor = "Agua" }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Agua")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = tipoMedidor == "Luz",
                    onClick = { tipoMedidor = "Luz" }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Luz")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = tipoMedidor == "Gas",
                    onClick = { tipoMedidor = "Gas" }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Gas")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val valorMedicion = numeroMedicion.toDoubleOrNull()
                if (valorMedicion != null && valorMedicion > 0 && fecha.isNotEmpty()) {
                    println("Datos válidos, insertando medición.")
                    val medicion = Medicion(
                        tipo = tipoMedidor,
                        valor = valorMedicion,
                        fecha = fecha
                    )
                    // Llamamos a la función para insertar la medición
                    scope.launch {
                        viewModel.insertarMedicion(medicion)

                        // Después de insertar la medición, actualizar la lista de mediciones
                        val updatedMediciones = viewModel.obtenerMediciones()
                        mediciones.clear()
                        mediciones.addAll(updatedMediciones)

                        // Navegamos a la pantalla de listado de mediciones
                        navController.navigate("listadoMediciones")
                    }
                } else {
                    println("Datos inválidos")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Medición")
        }
    }
}

package cl.stephanielazo.stephaniemedidores.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import cl.stephanielazo.stephaniemedidores.viewmodel.MedicionViewModel
import cl.stephanielazo.stephaniemedidores.modelo.Medicion
import kotlinx.coroutines.launch
import cl.stephanielazo.stephaniemedidores.R

@Composable
fun RegistroMedidorScreen(navController: NavController, viewModel: MedicionViewModel) {

    // Variables para guardar los valores introducidos
    var numeroMedicion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var tipoMedidor by remember { mutableStateOf("Agua") }

    // Strings para los tipos de medidor y textos del UI
    val agua = stringResource(id = R.string.agua)
    val luz = stringResource(id = R.string.luz)
    val gas = stringResource(id = R.string.gas)
    val medidor = stringResource(id = R.string.medidor)
    val fechaString = stringResource(id = R.string.fecha)
    val medidorDe = stringResource(id = R.string.medidor_de)
    val registrarMedicion = stringResource(id = R.string.registrar_medicion)
    val registroMedidor = stringResource(id = R.string.registro_medidor)

    val labelStyle = TextStyle(color = Color.Black)


    val mediciones = remember { mutableStateListOf<Medicion>() }

    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
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
            text = registroMedidor,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))


        TextField(
            value = numeroMedicion,
            onValueChange = { numeroMedicion = it },
            label = { Text(medidor, style = labelStyle) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))


        TextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text(fechaString, style = labelStyle) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(medidorDe)


        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = tipoMedidor == agua,
                    onClick = { tipoMedidor = agua }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(agua)
            }

            // Opción para Luz
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = tipoMedidor == luz,
                    onClick = { tipoMedidor = luz }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(luz)
            }

            // Opción para Gas
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = tipoMedidor == gas,
                    onClick = { tipoMedidor = gas }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(gas)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


        Button(
            onClick = {
                val valorMedicion = numeroMedicion.toDoubleOrNull()
                if (valorMedicion != null && valorMedicion > 0 && fecha.isNotEmpty()) {

                    println("Datos válidos, insertando medición. Tipo de medidor: $tipoMedidor")


                    val medicion = Medicion(
                        tipo = tipoMedidor,
                        valor = valorMedicion,
                        fecha = fecha
                    )


                    scope.launch {
                        viewModel.insertarMedicion(medicion)


                        val updatedMediciones = viewModel.obtenerMediciones()
                        mediciones.clear()
                        mediciones.addAll(updatedMediciones)


                        navController.navigate("listadoMediciones")
                    }
                } else {

                    println("Datos inválidos")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(registrarMedicion)
        }
    }
}

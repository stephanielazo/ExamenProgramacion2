package cl.stephanielazo.stephaniemedidores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cl.stephanielazo.stephaniemedidores.pantallas.ListadoMedicionesScreen
import cl.stephanielazo.stephaniemedidores.pantallas.RegistroMedidorScreen
import cl.stephanielazo.stephaniemedidores.ui.theme.StephanieMedidoresTheme
import cl.stephanielazo.stephaniemedidores.viewmodel.MedicionViewModel

class MainActivity : ComponentActivity() {

    private lateinit var medicionViewModel: MedicionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewModel con ViewModelProvider
        medicionViewModel = ViewModelProvider(this).get(MedicionViewModel::class.java)

        setContent {
            StephanieMedidoresTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // Crear el NavController
                    val navController = rememberNavController()

                    // Usar NavHost para la navegación
                    NavHost(navController = navController, startDestination = "registroMedidor") {
                        composable("registroMedidor") {
                            // Pasar el navController y viewModel a la pantalla de RegistroMedidor
                            RegistroMedidorScreen(navController = navController, viewModel = medicionViewModel)
                        }
                        composable("listadoMediciones") {
                            // Aquí no pasamos el navController a ListadoMedicionesScreen, solo pasamos el viewModel
                            ListadoMedicionesScreen(viewModel = medicionViewModel)
                        }
                    }
                }
            }
        }
    }
}

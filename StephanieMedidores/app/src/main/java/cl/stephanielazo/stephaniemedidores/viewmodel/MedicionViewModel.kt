package cl.stephanielazo.stephaniemedidores.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.stephanielazo.stephaniemedidores.modelo.Medicion
import cl.stephanielazo.stephaniemedidores.modelo.MedicionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MedicionViewModel(application: Application) : AndroidViewModel(application) {

    private val medicionDao = MedicionDatabase.getDatabase(application).medicionDao()

    // Función para obtener mediciones
    suspend fun obtenerMediciones(): List<Medicion> {
        return withContext(Dispatchers.IO) {
            medicionDao.obtenerMediciones() // Recupera las mediciones desde la DB
        }
    }

    // Función para insertar medición
    fun insertarMedicion(medicion: Medicion) {
        viewModelScope.launch(Dispatchers.IO) {
            medicionDao.insertarMedicion(medicion) // Inserta la medición en la DB
        }
    }
}

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


    suspend fun obtenerMediciones(): List<Medicion> {
        return withContext(Dispatchers.IO) {
            medicionDao.obtenerMediciones()
        }
    }


    fun insertarMedicion(medicion: Medicion) {
        viewModelScope.launch(Dispatchers.IO) {
            medicionDao.insertarMedicion(medicion)
        }
    }
}

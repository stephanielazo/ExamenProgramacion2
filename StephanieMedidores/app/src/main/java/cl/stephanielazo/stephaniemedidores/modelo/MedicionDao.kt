package cl.stephanielazo.stephaniemedidores.modelo

import androidx.room.*

@Dao
interface MedicionDao {

    // Insertar una medición en la base de datos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarMedicion(medicion: Medicion)

    // Obtener todas las mediciones, ordenadas por fecha descendente
    @Query("SELECT * FROM mediciones ORDER BY fecha DESC")
    suspend fun obtenerMediciones(): List<Medicion>  // Cambiado a List<Medicion> en lugar de Flow
}

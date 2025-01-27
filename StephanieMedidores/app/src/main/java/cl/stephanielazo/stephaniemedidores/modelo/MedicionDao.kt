package cl.stephanielazo.stephaniemedidores.modelo

import androidx.room.*

@Dao
interface MedicionDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarMedicion(medicion: Medicion)


    @Query("SELECT * FROM mediciones ORDER BY fecha DESC")
    suspend fun obtenerMediciones(): List<Medicion>
}

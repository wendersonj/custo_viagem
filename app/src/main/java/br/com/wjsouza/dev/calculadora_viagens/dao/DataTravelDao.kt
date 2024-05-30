package br.com.wjsouza.dev.calculadora_viagens.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.wjsouza.dev.calculadora_viagens.model.DataTravel

@Dao
interface DataTravelDao {
    @Insert
    fun insert(dataTravel: DataTravel)

    @Query("SELECT * from data_travel_table ORDER BY id DESC")
    fun getAllData() : LiveData<List<DataTravel>>


}
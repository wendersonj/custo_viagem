package com.example.calculadoraviagens3.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.calculadoraviagens3.model.DataTravel

@Dao
interface DataTravelDao {
    @Insert
    fun insert(dataTravel: DataTravel)

    @Query("SELECT * from data_travel_table ORDER BY id DESC")
    fun getAllData() : LiveData<List<DataTravel>>


}
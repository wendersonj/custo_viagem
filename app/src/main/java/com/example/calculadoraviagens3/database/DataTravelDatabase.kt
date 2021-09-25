package com.example.calculadoraviagens3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.calculadoraviagens3.dao.DataTravelDao
import com.example.calculadoraviagens3.model.DataTravel


@Database(entities = [DataTravel::class], version = 1, exportSchema = false)
abstract class DataTravelDatabase : RoomDatabase() {

    abstract val dataTravelDao: DataTravelDao

    companion object {

        @Volatile
        private var INSTANCE: DataTravelDatabase? = null

        fun getInstance(context: Context): DataTravelDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataTravelDatabase::class.java,
                        "data_travel_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
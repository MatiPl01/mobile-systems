package com.example.bmicalculator

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BmiMeasurement::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bmiMeasurementDao(): BmiMeasurementDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bmis.db"
                ).build().also { INSTANCE = it }
            }
    }
}

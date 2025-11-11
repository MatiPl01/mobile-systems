package com.example.bmicalculator

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BmiMeasurementDao {
    @Query("Select * from BmiMeasurements")
    fun getAll(): Flow<List<BmiMeasurement>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bmiMeasurement: BmiMeasurement): Long

    @Query("Delete from BmiMeasurements")
    suspend fun clear()

    @Delete
    suspend fun delete(bmiMeasurement: BmiMeasurement)
}
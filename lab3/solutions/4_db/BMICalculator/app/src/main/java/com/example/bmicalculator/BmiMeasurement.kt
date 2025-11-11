package com.example.bmicalculator

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="BmiMeasurements")
data class BmiMeasurement(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val weightKg: Double,
    val heightCm: Double,
    val bmi: Double,
    val category: String,
    val timestamp: Long // System.currentTimeMillis()
)

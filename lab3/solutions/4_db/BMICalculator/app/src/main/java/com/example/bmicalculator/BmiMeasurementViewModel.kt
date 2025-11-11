package com.example.bmicalculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.pow

class BmiMeasurementViewModel(app: Application) :
    AndroidViewModel(app) {
    private val repo =
        BmiMeasurementRepository(AppDatabase.get(app).bmiMeasurementDao())

    val history = repo.getAll().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    /** Oblicza BMI i zapisuje do bazy. Zwraca obliczony wynik
    przez callback. */
    fun calculateAndSave(
        weightKg: Double,
        heightCm: Double,
        onSaved: (BmiMeasurement) -> Unit = {}
    ) {
        val hMeters = heightCm / 100.0
        if (hMeters <= 0.0 || weightKg <= 0.0) return

        val bmi = weightKg / hMeters.pow(2.0)
        val cat = bmiCategory(bmi)
        val rec = BmiMeasurement(
            weightKg = weightKg,
            heightCm = heightCm,
            bmi = bmi,
            category = cat,
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            val id = repo.save(rec)
            onSaved(rec.copy(id = id))
        }
    }

    fun clearHistory() = viewModelScope.launch {
        repo.clear()
    }

    fun deleteMeasurement(measurement: BmiMeasurement) = viewModelScope.launch {
        repo.delete(measurement)
    }

    private fun bmiCategory(bmi: Double): String = BMIScale.diagnosis(bmi)
}
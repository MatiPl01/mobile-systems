package com.example.bmicalculatorwithstaticlayouts

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.pow

class BmiViewModel : ViewModel() {
    
    val weightText = MutableLiveData("")
    val heightText = MutableLiveData("")
    val bmiResultText = MutableLiveData("—")
    
    private var currentBmi: Double? = null
        set(value) {
            field = value
            updateBmiResultText()
        }
    
    companion object {
        const val BMI_MAX = 40.0
        private val BMI_THRESHOLDS = listOf(18.5, 24.9, 29.9, 34.9)
        private val BMI_CATEGORIES = listOf(
            "Underweight",
            "Healthy",
            "Overweight",
            "Obesity",
            "Extreme obesity"
        )
    }
    
    fun calculateBmi(view: View? = null) {
        val weight = weightText.value?.toDoubleOrNull()
        val height = heightText.value?.toDoubleOrNull()
        
        currentBmi = if (weight != null && height != null && height > 0.0) {
            weight / height.pow(2)
        } else {
            null
        }
    }
    
    private fun updateBmiResultText() {
        val text = currentBmi?.let { bmi ->
            val category = getBmiCategory(bmi)
            "Your BMI: ${"%.2f".format(bmi)} — $category"
        } ?: "—"
        bmiResultText.value = text
    }
    
    private fun getBmiCategory(bmi: Double): String = when {
        bmi < BMI_THRESHOLDS[0] -> BMI_CATEGORIES[0]
        bmi < BMI_THRESHOLDS[1] -> BMI_CATEGORIES[1]
        bmi < BMI_THRESHOLDS[2] -> BMI_CATEGORIES[2]
        bmi < BMI_THRESHOLDS[3] -> BMI_CATEGORIES[3]
        else -> BMI_CATEGORIES[4]
    }
    
    fun getCurrentBmi(): Double? = currentBmi
}


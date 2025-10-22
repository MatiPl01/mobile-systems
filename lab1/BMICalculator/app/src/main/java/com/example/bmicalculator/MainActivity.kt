package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val weight = remember { mutableStateOf("Type your weight in KG") }
      val height = remember { mutableStateOf("Type your height in M") }
      val bmi = remember { mutableStateOf("BMI") }

      BMICalculatorTheme {
        Column(modifier = Modifier.fillMaxSize().safeContentPadding(), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
          TextField(value = weight.value, onValueChange = {
            weight.value = it
          }, modifier = Modifier.fillMaxWidth())
          TextField(value = height.value, onValueChange = {
            height.value = it
          }, modifier = Modifier.fillMaxWidth())
          Button(onClick = {

          }) {
            Text("Calculate my BMI")
          }

          Text(bmi.value)
        }
      }
    }
  }
}

fun calculateBMI(weight: Double, height: Double): String {
  val bmi = weight / (height * height)

  if (bmi < 18.5) { return "Underweight" }
  if (bmi < 24.9) { return "Healthy" }
  if (bmi < 29.9) { return "Overweight" }
  if (bmi < 34.9) { return "Obesity" }

  return "Elephant"
}

package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bmicalculator.ui.theme.BMICalculatorTheme
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

private const val BMI_MAX = 40.0

object BMIScale {
    val thresholds = listOf(18.5, 24.9, 29.9, 34.9)
    val categories = listOf("Underweight", "Healthy", "Overweight", "Obesity", "Extreme obesity")
    val colors = listOf(
        Color(0xFF64B5F6),
        Color(0xFF81C784),
        Color(0xFFFFB74D),
        Color(0xFFE57373)
    )

    fun diagnosis(bmi: Double?): String = when {
        bmi == null || bmi <= 0.0 -> "—"
        bmi < thresholds[0] -> categories[0]
        bmi < thresholds[1] -> categories[1]
        bmi < thresholds[2] -> categories[2]
        bmi < thresholds[3] -> categories[3]
        else -> categories[4]
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val weight = rememberSaveable { mutableStateOf("") }
            val height = rememberSaveable { mutableStateOf("") }
            val bmi = rememberSaveable { mutableStateOf<Double?>(null) }

            BMICalculatorTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .safeContentPadding(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = weight.value,
                        onValueChange = { weight.value = it },
                        label = { Text("Type your weight in KG") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = height.value,
                        onValueChange = { height.value = it },
                        label = { Text("Type your height in M") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(onClick = {
                        val w = weight.value.toDoubleOrNull()
                        val h = height.value.toDoubleOrNull()
                        bmi.value = if (w != null && h != null && h > 0.0) w / (h * h) else null
                    }) { Text("Calculate my BMI") }

                    val txt =
                        bmi.value?.let { "%.2f — %s".format(it, BMIScale.diagnosis(it)) } ?: "—"
                    Text("Your BMI: $txt")

                    BMIMeter(bmiValue = bmi.value)
                }
            }
        }
    }
}

@Composable
fun BMIMeter(
    bmiValue: Double?,
    modifier: Modifier = Modifier,
    barHeight: Dp = 24.dp,
    markerSize: Dp = 16.dp,
) {
    val density = LocalDensity.current
    val barWidthPxState = rememberSaveable { mutableIntStateOf(0) }
    val barWidthPx = barWidthPxState.intValue.toFloat()

    val clamped = bmiValue?.coerceIn(0.0, BMI_MAX)
    val normalized = clamped?.let { (it / BMI_MAX).toFloat() }

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val numberTicks = listOf(0.0) + BMIScale.thresholds

        if (barWidthPx > 0f) {
            NumberTicks(
                totalWidthPx = barWidthPx.roundToInt(),
                ticks = numberTicks
            )
        } else {
            Spacer(Modifier.height(1.dp))
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth()
                    .height(barHeight)
                    .background(
                        Brush.horizontalGradient(BMIScale.colors),
                        shape = RoundedCornerShape(barHeight / 2)
                    )
                    .onGloballyPositioned { coordinates ->
                        barWidthPxState.intValue = coordinates.size.width
                    }
            )

            if (barWidthPx > 0f) {
                BMIScale.thresholds.forEach { t ->
                    val x = with(density) { (barWidthPx * (t / BMI_MAX).toFloat()).toDp() }
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(barHeight)
                            .offset(x = x - 1.dp)
                            .align(Alignment.CenterStart)
                            .background(Color.White.copy(alpha = 0.9f))
                    )
                }

                if (normalized != null && clamped > 0.0) {
                    val markerPx = with(density) { markerSize.toPx() }
                    val half = markerPx / 2f
                    val raw = barWidthPx * normalized
                    val clampedX = min(max(raw, half), barWidthPx - half)
                    val xDp = with(density) { (clampedX - half).toDp() }
                    Box(
                        modifier = Modifier
                            .size(markerSize)
                            .offset(x = xDp)
                            .align(Alignment.CenterStart)
                            .background(Color.White, CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
private fun NumberTicks(
    totalWidthPx: Int,
    ticks: List<Double>
) {
    SubcomposeLayout(modifier = Modifier.fillMaxWidth()) { constraints ->
        val measurables = ticks.map { t ->
            val label = if (t == t.toInt().toDouble()) t.toInt().toString() else "%.1f".format(t)
            subcompose("tick-$t") { Text(text = label, textAlign = TextAlign.Center) }.first()
        }
        val placeables =
            measurables.map { it.measure(constraints.copy(minWidth = 0, minHeight = 0)) }

        val w = constraints.maxWidth
        val h = placeables.maxOfOrNull { it.height } ?: 0

        fun xAt(v: Double) = (totalWidthPx * (v / BMI_MAX)).roundToInt()

        layout(w, h) {
            ticks.indices.forEach { i ->
                val p = placeables[i]
                val cx = xAt(ticks[i])
                val x = (cx - p.width / 2).coerceIn(0, w - p.width)
                p.place(x, 0)
            }
        }
    }
}

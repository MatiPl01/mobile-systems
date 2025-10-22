package com.example.mateuszlopacinskilaboratory1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val ctx = LocalContext.current
      Column {
        Button(onClick = {
          Toast.makeText(ctx, "Hello there!", LENGTH_LONG).show()
        }, modifier = Modifier.height(100.dp)) {
          Text("My first button")
        }
      }
    }
  }
}

@Composable
fun MyTexts() {
  Column(
    verticalArrangement = Arrangement.SpaceEvenly,
    horizontalAlignment = Alignment.End,
    modifier = Modifier.fillMaxSize()
  ) {
    Text(text = "Hello World!", color = Color.Red, fontSize = 20.sp, letterSpacing = 3.sp)
    Text(
      text = "I'm doing so great",
      color = Color.Blue,
      fontSize = 20.sp,
      letterSpacing = 3.sp
    )
    Text(
      text = "And android is so cool",
      color = Color.Magenta,
      fontSize = 20.sp,
      letterSpacing = 3.sp
    )
  }
}

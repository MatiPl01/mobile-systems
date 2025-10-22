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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val ctx = LocalContext.current
      val txt: MutableState<String> = remember {
        mutableStateOf("This text will be changed")
      }
      val shouldTextsBePresented: MutableState<Boolean> = remember {
        mutableStateOf(false)
      }
      val tfValue: MutableState<String> = remember {
        mutableStateOf("My value")
      }

      Column(modifier = Modifier
        .statusBarsPadding()
        .navigationBarsPadding()) {
        Button(onClick = {
          Log.i("myapp", "Entry from my application")
        }) {
          Text("Log to LogCat")
        }

        Button(onClick = {
          Toast.makeText(ctx, "Hello there!", LENGTH_LONG).show()
        }) {
          Text("My first button")
        }

        Button(onClick = {
          txt.value = "My new text"
        }) {
          Text("Change the text")
        }

        Text(text = txt.value, fontSize = 20.sp)

        Button(onClick = {
          shouldTextsBePresented.value = !shouldTextsBePresented.value
        }) {
          Text("${if (shouldTextsBePresented.value) "Hide" else "Display"} colored text")
        }

        TextField(value = tfValue.value, onValueChange = {
          tfValue.value = it
        })

        if (shouldTextsBePresented.value) {
          MyTexts()
        }
      }
    }
  }
}

@Composable
fun MyTexts() {
  Column(
    horizontalAlignment = Alignment.End,
    verticalArrangement = Arrangement.Bottom,
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

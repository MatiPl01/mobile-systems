package com.example.mateuszlopacinskilaboratory3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mateuszlopacinskilaboratory3.ui.theme.MateuszLopacinskiLaboratory3Theme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MateuszLopacinskiLaboratory3Theme {
        Scaffold(
          modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
        ) { innerPadding ->
          FirstActivityWidgets(
            buttonText = "New Activity",
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun FirstActivityWidgets(
  buttonText: String, modifier:
  Modifier = Modifier
) {
  val context = LocalContext.current
  val textForIntent = remember {
    mutableStateOf("")
  }

  Column {
    TextField(value = textForIntent.value, onValueChange = {
      textForIntent.value = it
    })
    Button(modifier = modifier, onClick = {
      val intent =
        Intent(context, GalleryActivity::class.java)
          .putExtra("nazwa", textForIntent.value)
      context.startActivity(intent)
    }) {
      Text(buttonText)
    }
  }
}

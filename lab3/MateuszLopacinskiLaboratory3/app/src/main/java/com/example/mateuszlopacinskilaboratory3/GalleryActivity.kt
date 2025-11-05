package com.example.mateuszlopacinskilaboratory3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.mateuszlopacinskilaboratory3.ui.theme.MateuszLopacinskiLaboratory3Theme

class GalleryActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    val forwardedText = intent.getStringExtra("nazwa") ?: "Android"

    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MateuszLopacinskiLaboratory3Theme {
        Scaffold(
          modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
        ) { innerPadding ->
          GalleryActivityWidgets(
            forwardedText = forwardedText,
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun GalleryActivityWidgets(forwardedText: String, modifier: Modifier = Modifier) {
  val context = LocalContext.current

  Column {
    Text(text = "Hello $forwardedText!")

    Button(
      modifier = modifier,
      onClick = {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
      },
    ) {
      Text("Go back")
    }
  }
}

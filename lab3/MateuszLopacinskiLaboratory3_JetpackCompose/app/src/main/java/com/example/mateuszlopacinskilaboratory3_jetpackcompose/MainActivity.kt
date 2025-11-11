package com.example.mateuszlopacinskilaboratory3_jetpackcompose

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mateuszlopacinskilaboratory3_jetpackcompose.ui.theme.MateuszLopacinskiLaboratory3_JetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MateuszLopacinskiLaboratory3_JetpackComposeTheme {
                AppNav()
            }
        }
    }
}

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.screenA) {
        composable(Routes.screenA) { ScreenA(navController) }
        composable(
            route = "${Routes.screenB}/{text}",
            arguments = listOf(
                navArgument("text") {
                    type = NavType.StringType
                    nullable = false
                })
        ) { entry ->
            val text = Uri.decode(entry.arguments?.getString("text").orEmpty())
            ScreenB(navController, text = text)
        }
    }
}

@Composable
fun ScreenA(nav: NavController) {
    val textForScreenB: MutableState<String> = remember {
        mutableStateOf("")
    }

    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(8.dp))
            Text("Ekran A", fontSize = 24.sp)
            Spacer(Modifier.height(10.dp))
            TextField(
                value = textForScreenB.value, onValueChange = { textForScreenB.value = it })
            Spacer(Modifier.height(10.dp))
            Button(onClick = {
                val encodedText = Uri.encode(textForScreenB.value).ifBlank { "Android" }
                nav.navigate("${Routes.screenB}/$encodedText")
            }) {
                Text("Przejdź do ekranu B")
            }
        }
    }
}

@Composable
fun ScreenB(nav: NavController, text: String) {
    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(8.dp))
            Text("Ekran B", fontSize = 24.sp)
            Spacer(Modifier.height(10.dp))
            Text("Hello, $text!")
            Spacer(Modifier.height(10.dp))
            Button(onClick = { nav.popBackStack() }) {
                Text("Wróć")
            }
        }
    }
}

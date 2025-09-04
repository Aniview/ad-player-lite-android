package com.adservrs.adplayer.lite.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.adservrs.adplayer.lite.example.ui.theme.AdplayerliteandroidexampleTheme

const val PUB_ID = "565c56d3181f46bd608b459a"
const val TAG_ID = "67dd7d7835b4a37b880f2b3c"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AdplayerliteandroidexampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MainScreen(modifier = Modifier.padding(it))
                }
            }
        }
    }
}

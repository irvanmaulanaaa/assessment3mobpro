package com.irvanmaulana0013.barangku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.irvanmaulana0013.barangku.ui.screen.MainScreen
import com.irvanmaulana0013.barangku.ui.theme.BarangkuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BarangkuTheme {
                MainScreen()
            }
        }
    }
}
package com.example.quoteoftheday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.quoteoftheday.screen.QuoteApp
import com.example.quoteoftheday.ui.theme.QuoteOfTheDayTheme

class MainActivity : ComponentActivity() {
    private val quoteViewModel: QuoteViewModel by viewModels {
        QuoteViewModelFactory((application as QuoteApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuoteOfTheDayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuoteApp(quoteViewModel)
                }
            }
        }
    }
}

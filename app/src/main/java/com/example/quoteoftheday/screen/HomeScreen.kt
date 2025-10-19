package com.example.quoteoftheday.screen

import android.content.Context
import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quoteoftheday.QuoteViewModel
import com.example.quoteoftheday.data.Quote

@Composable
fun HomeScreen(viewModel: QuoteViewModel) {
    val context = LocalContext.current
    val currentQuote by viewModel.currentQuote.observeAsState()
    val isFavorite by viewModel.isFavorite.observeAsState(false)

    currentQuote?.let { quote ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF252541)
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.FormatQuote,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color(0xFF6B4EFF)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "\"${quote.text}\"",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        lineHeight = 32.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "— ${quote.author}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFB0B0C8),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton(
                    icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    text = if (isFavorite) "Saved" else "Save",
                    color = Color(0xFFFF6B9D),
                    onClick = {
                        viewModel.toggleFavorite(quote)
                    }
                )

                ActionButton(
                    icon = Icons.Default.Share,
                    text = "Share",
                    color = Color(0xFF6B4EFF),
                    onClick = {
                        shareQuote(context, quote)
                    }
                )

                ActionButton(
                    icon = Icons.Default.Refresh,
                    text = "New Quote",
                    color = Color(0xFF4ECDC4),
                    onClick = { viewModel.loadRandomQuote() }
                )
            }
        }
    }
}

fun shareQuote(context: Context, quote: Quote) {
    val shareText = "\"${quote.text}\"\n\n— ${quote.author}\n\nShared via Quote of the Day"

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, "Share Quote")
    context.startActivity(shareIntent)
}
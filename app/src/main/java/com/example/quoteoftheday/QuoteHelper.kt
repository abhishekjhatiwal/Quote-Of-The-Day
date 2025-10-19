package com.example.quoteoftheday

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

// Data class for Quote
data class Quote(
    val id: Int,
    val text: String,
    val author: String,
    var isFavorite: Boolean = false
)

// Quote Repository
object QuoteRepository {
    private val quotes = listOf(
        Quote(1, "The only way to do great work is to love what you do.", "Steve Jobs"),
        Quote(2, "Innovation distinguishes between a leader and a follower.", "Steve Jobs"),
        Quote(3, "Life is what happens when you're busy making other plans.", "John Lennon"),
        Quote(4, "The future belongs to those who believe in the beauty of their dreams.", "Eleanor Roosevelt"),
        Quote(5, "It is during our darkest moments that we must focus to see the light.", "Aristotle"),
        Quote(6, "Whoever is happy will make others happy too.", "Anne Frank"),
        Quote(7, "Do not go where the path may lead, go instead where there is no path and leave a trail.", "Ralph Waldo Emerson"),
        Quote(8, "You will face many defeats in life, but never let yourself be defeated.", "Maya Angelou"),
        Quote(9, "The greatest glory in living lies not in never falling, but in rising every time we fall.", "Nelson Mandela"),
        Quote(10, "In the end, it's not the years in your life that count. It's the life in your years.", "Abraham Lincoln"),
        Quote(11, "Never let the fear of striking out keep you from playing the game.", "Babe Ruth"),
        Quote(12, "Life is either a daring adventure or nothing at all.", "Helen Keller"),
        Quote(13, "Many of life's failures are people who did not realize how close they were to success when they gave up.", "Thomas Edison"),
        Quote(14, "You have brains in your head. You have feet in your shoes. You can steer yourself any direction you choose.", "Dr. Seuss"),
        Quote(15, "Believe you can and you're halfway there.", "Theodore Roosevelt")
    )

    private val favorites = mutableStateListOf<Quote>()

    fun getQuoteForDate(date: Date): Quote {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val seed = dateFormat.format(date).toLong()
        val random = Random(seed)
        return quotes[random.nextInt(quotes.size)]
    }

    fun getRandomQuote(): Quote {
        return quotes.random()
    }

    fun toggleFavorite(quote: Quote) {
        val existing = favorites.find { it.id == quote.id }
        if (existing != null) {
            favorites.remove(existing)
        } else {
            favorites.add(quote.copy(isFavorite = true))
        }
    }

    fun isFavorite(quoteId: Int): Boolean {
        return favorites.any { it.id == quoteId }
    }

    fun getFavorites(): List<Quote> = favorites
}

//@Composable
//fun QuoteOfTheDayTheme(content: @Composable () -> Unit) {
//    MaterialTheme(
//        colorScheme = darkColorScheme(
//            primary = Color(0xFF6B4EFF),
//            secondary = Color(0xFFFF6B9D),
//            background = Color(0xFF0F0F1E),
//            surface = Color(0xFF1A1A2E)
//        ),
//        content = content
//    )
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteApp() {
    var selectedScreen by remember { mutableStateOf("home") }
    var currentQuote by remember { mutableStateOf(QuoteRepository.getQuoteForDate(Date())) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Quote of the Day",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A1A2E),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = selectedScreen,
                onScreenSelected = { selectedScreen = it }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0F0F1E),
                            Color(0xFF1A1A2E)
                        )
                    )
                )
        ) {
            when (selectedScreen) {
                "home" -> HomeScreen(
                    quote = currentQuote,
                    onRefresh = { currentQuote = QuoteRepository.getRandomQuote() }
                )
                "favorites" -> FavoritesScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(quote: Quote, onRefresh: () -> Unit) {
    var isFavorite by remember(quote.id) {
        mutableStateOf(QuoteRepository.isFavorite(quote.id))
    }

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
                    QuoteRepository.toggleFavorite(quote)
                    isFavorite = !isFavorite
                }
            )

            ActionButton(
                icon = Icons.Default.Share,
                text = "Share",
                color = Color(0xFF6B4EFF),
                onClick = {
                    // Share functionality would be implemented here
                    // For now, it's a placeholder
                }
            )

            ActionButton(
                icon = Icons.Default.Refresh,
                text = "New Quote",
                color = Color(0xFF4ECDC4),
                onClick = onRefresh
            )
        }
    }
}

@Composable
fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = color,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFFB0B0C8)
        )
    }
}

@Composable
fun FavoritesScreen() {
    val favorites = QuoteRepository.getFavorites()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Your Favorite Quotes",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color(0xFF6B4EFF).copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No favorites yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFB0B0C8)
                    )
                    Text(
                        text = "Start saving quotes you love!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF808095)
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favorites) { quote ->
                    FavoriteQuoteCard(quote)
                }
            }
        }
    }
}

@Composable
fun FavoriteQuoteCard(quote: Quote) {
    var isFavorite by remember { mutableStateOf(true) }

    if (isFavorite) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF252541)
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "\"${quote.text}\"",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        lineHeight = 24.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "— ${quote.author}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFB0B0C8)
                    )
                }

                IconButton(
                    onClick = {
                        QuoteRepository.toggleFavorite(quote)
                        isFavorite = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Remove from favorites",
                        tint = Color(0xFFFF6B9D)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedScreen: String,
    onScreenSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFF1A1A2E),
        contentColor = Color.White
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedScreen == "home",
            onClick = { onScreenSelected("home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF6B4EFF),
                selectedTextColor = Color(0xFF6B4EFF),
                unselectedIconColor = Color(0xFF808095),
                unselectedTextColor = Color(0xFF808095),
                indicatorColor = Color(0xFF6B4EFF).copy(alpha = 0.2f)
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
            selected = selectedScreen == "favorites",
            onClick = { onScreenSelected("favorites") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFFFF6B9D),
                selectedTextColor = Color(0xFFFF6B9D),
                unselectedIconColor = Color(0xFF808095),
                unselectedTextColor = Color(0xFF808095),
                indicatorColor = Color(0xFFFF6B9D).copy(alpha = 0.2f)
            )
        )
    }
}
package com.example.quoteoftheday.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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
package com.example.quoteoftheday

import android.app.Application
import com.example.quoteoftheday.data.QuoteDatabase
import com.example.quoteoftheday.data.QuoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class QuoteApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    val database: QuoteDatabase by lazy {
        QuoteDatabase.getDatabase(this, applicationScope)
    }

    val repository: QuoteRepository by lazy {
        QuoteRepository(database.quoteDao())
    }
}
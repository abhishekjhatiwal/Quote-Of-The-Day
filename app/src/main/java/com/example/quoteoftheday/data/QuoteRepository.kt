package com.example.quoteoftheday.data

import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class QuoteRepository(private val quoteDao: QuoteDao) {

    val allFavorites: Flow<List<Quote>> = quoteDao.getFavoriteQuotes()

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

    fun getQuoteForDate(date: Date): Quote {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val seed = dateFormat.format(date).toLong()
        val random = Random(seed)
        return quotes[random.nextInt(quotes.size)]
    }

    fun getRandomQuote(): Quote {
        return quotes.random()
    }

    suspend fun toggleFavorite(quote: Quote) {
        val existingQuote = quoteDao.getQuoteById(quote.id)
        if (existingQuote != null && existingQuote.isFavorite) {
            quoteDao.updateQuote(quote.copy(isFavorite = false))
        } else {
            quoteDao.insertQuote(quote.copy(isFavorite = true))
        }
    }

    suspend fun isFavorite(quoteId: Int): Boolean {
        val quote = quoteDao.getQuoteById(quoteId)
        return quote?.isFavorite ?: false
    }
}
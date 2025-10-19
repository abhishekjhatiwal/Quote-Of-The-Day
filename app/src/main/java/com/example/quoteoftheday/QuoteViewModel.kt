package com.example.quoteoftheday


import androidx.lifecycle.*
import com.example.quoteoftheday.data.Quote
import com.example.quoteoftheday.data.QuoteRepository
import kotlinx.coroutines.launch
import java.util.*

class QuoteViewModel(private val repository: QuoteRepository) : ViewModel() {

    val allFavorites: LiveData<List<Quote>> = repository.allFavorites.asLiveData()

    private val _currentQuote = MutableLiveData<Quote>()
    val currentQuote: LiveData<Quote> = _currentQuote

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    init {
        loadQuoteOfTheDay()
    }

    fun loadQuoteOfTheDay() {
        val quote = repository.getQuoteForDate(Date())
        _currentQuote.value = quote
        checkIfFavorite(quote.id)
    }

    fun loadRandomQuote() {
        val quote = repository.getRandomQuote()
        _currentQuote.value = quote
        checkIfFavorite(quote.id)
    }

    private fun checkIfFavorite(quoteId: Int) {
        viewModelScope.launch {
            _isFavorite.value = repository.isFavorite(quoteId)
        }
    }

    fun toggleFavorite(quote: Quote) {
        viewModelScope.launch {
            repository.toggleFavorite(quote)
            checkIfFavorite(quote.id)
        }
    }
}

class QuoteViewModelFactory(private val repository: QuoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.firasalshawa.quoty.UI

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.firasalshawa.quoty.repository.QuoteRepository

class QuoteViewModelProviderFactory(
    val app:Application,
    val quoteRepository: QuoteRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  QuoteViewModel(app,quoteRepository) as T
    }
}
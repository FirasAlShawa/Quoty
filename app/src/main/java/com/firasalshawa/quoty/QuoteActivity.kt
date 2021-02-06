package com.firasalshawa.quoty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.firasalshawa.quoty.UI.QuoteViewModel
import com.firasalshawa.quoty.UI.QuoteViewModelProviderFactory
import com.firasalshawa.quoty.repository.QuoteRepository
import com.firasalshawa.quoty.util.Resource
import kotlinx.android.synthetic.main.activity_main.*

class QuoteActivity : AppCompatActivity() {

    lateinit var viewModel : QuoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = QuoteRepository()
        val viewModelProviderFactory = QuoteViewModelProviderFactory(application,repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(QuoteViewModel::class.java)

        viewModel.getRandomQuote()

        testQuote.text = viewModel.singleQuoteResponse.value.toString()

        viewModel.singleQuoteResponse.observe(this, Observer { res ->
            when (res) {
                is Resource.Success -> {
                    res.data?.let { quoteResponse ->
                        testQuote.text = quoteResponse.quote
                    }
                }

                is Resource.Error -> {
                    res.message?.let { message ->
                        Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
package com.firasalshawa.quoty.UI.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.firasalshawa.quoty.QuoteActivity
import com.firasalshawa.quoty.R
import com.firasalshawa.quoty.UI.QuoteViewModel
import com.firasalshawa.quoty.util.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*

class Home : Fragment(R.layout.fragment_home) {

    lateinit var viewModel : QuoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = (activity as QuoteActivity).viewModel
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getRandomQuote()

        tvQuote.text = viewModel.singleQuoteResponse.value.toString()

        viewModel.singleQuoteResponse.observe(viewLifecycleOwner, Observer { res ->
            when (res) {
                is Resource.Success -> {
                    res.data?.let { quoteResponse ->
                        tvQuote.text = quoteResponse.quote
                    }
                }

                is Resource.Error -> {
                    res.message?.let { message ->
                   val snackBar = Snackbar.make(
                            (activity as QuoteActivity).findViewById(android.R.id.content),
                            "$message", Snackbar.LENGTH_LONG
                        )
                        snackBar.show()
                    }
                }
            }
        })

    }


//        testQuote.setOnClickListener {
//        val quote:QuoteResponse = viewModel.singleQuoteResponse.value?.data as QuoteResponse
////           Fav -> viewModel.updateFav(quote)
////           Delete -> viewModel.deleteQuoteDB(quote)
//        }

    //Quote get all Quotes
//        viewModel.getAllQuotes()
//
//        viewModel.getAllQuotes().observe(this, Observer { list ->
//        updare adapter here
//            for(quote in list){
//                Log.i("Firas",quote.toString())
//            }
//        })

}
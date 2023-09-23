package com.firasalshawa.quoty.UI.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.firasalshawa.quoty.QuoteActivity
import com.firasalshawa.quoty.R
import com.firasalshawa.quoty.UI.QuoteViewModel
import com.firasalshawa.quoty.models.QuoteResponse
import com.firasalshawa.quoty.util.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*

class Home : Fragment(R.layout.fragment_home) {

    lateinit var viewModel : QuoteViewModel
   lateinit var  currentQuote: QuoteResponse
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = (activity as QuoteActivity).viewModel
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    @SuppressLint("UseCompatLoadingForDrawables", "SuspiciousIndentation", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            viewModel.getRandomQuote()

        tvQuote.text = viewModel.singleQuoteResponse.value.toString()

        viewModel.singleQuoteResponse.observe(viewLifecycleOwner, Observer { res ->
            when (res) {
                is Resource.Success -> {
                    res.data?.let { quoteResponse ->
                        tvQuote.text = quoteResponse.quote
                        tvAuthorHome.text = quoteResponse.author
                        btnLikeHome.isEnabled = true
                    }
                }

                is Resource.Error -> {
                    res.message?.let { message ->
                   val snackBar = Snackbar.make(
                            (activity as QuoteActivity).findViewById(android.R.id.content),
                       message, Snackbar.LENGTH_LONG
                        )
                        snackBar.show()
                    }
                    tvQuote.text = "Try\nagain\nlater"
                    btnLikeHome.isEnabled = false
                }

                is Resource.Loading -> {
                    tvQuote.text = "..."
                    tvAuthorHome.text = "..."
                    btnLikeHome.isEnabled = false
                }
            }
        })

        viewModel.offlineQuote.observe(viewLifecycleOwner, Observer { quote ->
            if(quote != null){
                tvQuote.text = quote.quote
                tvAuthorHome.text = quote.author
            }
        })


        btnLikeHome.setOnClickListener {
            val currentQuote: QuoteResponse
            if(viewModel.isDeviceOnline()){
                 currentQuote= viewModel.singleQuoteResponse.value?.data as QuoteResponse
            }else{
                 currentQuote= viewModel.offlineQuote.value as QuoteResponse
            }
            viewModel.updateFav(currentQuote)

            if(currentQuote.fav == false )
            {
                btnLikeHome.setImageDrawable(activity?.resources?.getDrawable(R.drawable.ic_baseline_favorite_24))
            }else{
                btnLikeHome.setImageDrawable(activity?.resources?.getDrawable(R.drawable.ic_baseline_favorite_border_24))
            }
//            Delete -> viewModel.deleteQuoteDB(quote)
        }


    }
}
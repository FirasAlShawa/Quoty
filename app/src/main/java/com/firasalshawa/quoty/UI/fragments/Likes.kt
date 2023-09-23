package com.firasalshawa.quoty.UI.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firasalshawa.quoty.QuoteActivity
import com.firasalshawa.quoty.R
import com.firasalshawa.quoty.UI.QuoteViewModel
import com.firasalshawa.quoty.adapter.QuoteAdapter
import kotlinx.android.synthetic.main.fragment_likes.*

class Likes : Fragment(R.layout.fragment_likes) {

    lateinit var viewModel : QuoteViewModel
    lateinit var quoteAdapter : QuoteAdapter

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

        setupRecyclerView()

        viewModel.getAllLikedQuotes().observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                emptyStateLotti.visibility = View.VISIBLE
                rvLikedQuotes.visibility = View.GONE
            } else {
                emptyStateLotti.visibility = View.GONE
                rvLikedQuotes.visibility = View.VISIBLE
                quoteAdapter.differ.submitList(list)
            }
        }

        quoteAdapter.setOnLikeClickListener { quote ->
            viewModel.updateFav(quote)
        }
    }

    private fun setupRecyclerView(){
        quoteAdapter = QuoteAdapter()
        rvLikedQuotes.apply {
            adapter = quoteAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }




}
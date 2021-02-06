package com.firasalshawa.quoty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.firasalshawa.quoty.R
import com.firasalshawa.quoty.models.QuoteResponse
import kotlinx.android.synthetic.main.like_quote_layout.view.*

class QuoteAdapter : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>(){

    inner class QuoteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<QuoteResponse>(){
        override fun areItemsTheSame(oldItem: QuoteResponse, newItem: QuoteResponse): Boolean {
            return oldItem.permalink == newItem.permalink
        }

        override fun areContentsTheSame(oldItem: QuoteResponse, newItem: QuoteResponse): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        return QuoteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.like_quote_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = differ.currentList[position]
        holder.itemView.apply {
            tvQuoteLayout.text = quote.quote
            tvAuthorLayout.text = quote.author
            if(quote.fav){
                btnLikeLayout.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_favorite_24))
            }else{
                btnLikeLayout.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_favorite_border_24))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
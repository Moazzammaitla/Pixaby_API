package com.example.pxabay_api_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class QuoteAdapter(private var quotes: List<Hit>) :
    RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val likesTextView: TextView = itemView.findViewById(R.id.likesTextView)
        val commentsTextView: TextView = itemView.findViewById(R.id.commentsTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quote, parent, false)
        return QuoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quotes[position]

        // Load and display the image using Glide
        Glide.with(holder.itemView.context)
            .load(quote.largeImageURL)
            .into(holder.imageView)

        holder.likesTextView.text = "Likes: ${quote.likes}"
        holder.commentsTextView.text = "Comments: ${quote.comments}"
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    // Update the data when needed
    fun updateData(newData: List<Hit>) {
        quotes = newData
        notifyDataSetChanged()
    }
}
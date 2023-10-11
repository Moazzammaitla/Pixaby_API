package com.example.pxabay_api_project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class QuoteAdapter(
    private var context: Context,
    private var quotes: List<Hit>,
    private val onFavoriteClick: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val likesTextView: TextView = itemView.findViewById(R.id.likesTextView)
        val commentsTextView: TextView = itemView.findViewById(R.id.commentsTextView)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.favoriteButton) // Add this line
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

        // Set the favorite button click listener
        holder.favoriteButton.setOnClickListener {
            val isFavorite = !quote.isFavorite
//            Toast.makeText(context,"$isFavorite", Toast.LENGTH_LONG).show()

            onFavoriteClick(position, isFavorite)
        }

        // Set the favorite button's icon based on the isFavorite state
        val favoriteIcon = if (quote.isFavorite) {
            R.drawable.favorite // Use the filled heart icon for favorites
        } else {
            R.drawable.baseline_favorite_border_24 // Use the outlined heart icon for non-favorites
        }
        holder.favoriteButton.setImageResource(favoriteIcon)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    // Update the data when needed
    fun updateData(newData: List<Hit>) {
        quotes = newData
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Any): Hit {
        return quotes[position as Int]
    }

    fun notifyItemChanged(position: Any) {

    }
}
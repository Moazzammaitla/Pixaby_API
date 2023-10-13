package com.example.pxabay_api_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FavoriteAdapter(
    private var favorites: List<FavoriteHit>,
    private val onRemoveFavorite: (FavoriteHit) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.favoriteImageView)
        val likesTextView: TextView = itemView.findViewById(R.id.favoriteLikesTextView)
        val commentsTextView: TextView = itemView.findViewById(R.id.favoriteCommentsTextView)
        val removeFavoriteButton: ImageView = itemView.findViewById(R.id.removeFavoriteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favorites[position]

        // Load and display the image using Glide
        Glide.with(holder.itemView.context)
            .load(favorite.largeImageURL)
            .into(holder.imageView)

        holder.likesTextView.text = "Likes: ${favorite.likes}"
        holder.commentsTextView.text = "Comments: ${favorite.comments}"

        // Set the remove favorite button click listener
        holder.removeFavoriteButton.setOnClickListener {
            onRemoveFavorite(favorite)
        }
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    // Update the data when needed
    fun updateData(newData: List<FavoriteHit>) {
        favorites = newData
        notifyDataSetChanged()
    }
}
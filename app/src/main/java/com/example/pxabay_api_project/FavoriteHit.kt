package com.example.pxabay_api_project

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteHit (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imageId: Int, // You may need to adjust this based on your API response
    val largeImageURL: String,
    val likes: Int,
    val comments: Int,
    // Add other properties as needed

)
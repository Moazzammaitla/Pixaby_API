package com.example.pxabay_api_project

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteHit (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imageId: Int,
    val largeImageURL: String,
    val likes: Int,
    val comments: Int,
    // Add other properties as needed

)
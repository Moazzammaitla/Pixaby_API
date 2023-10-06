package com.example.pxabay_api_project

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuoteDao{

    @Insert
    suspend fun addQuotes(quotes: List<Hit>)

    @Query("SELECT * FROM quote")
    suspend fun getQuotes() : List<Hit>
}
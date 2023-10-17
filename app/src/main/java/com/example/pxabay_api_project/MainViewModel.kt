package com.example.pxabay_api_project

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuoteRepository,
                    private val favoriteDao: FavoriteDao) : ViewModel() {

    /*init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getQuotes()
        }
    }*/

    val quotes : LiveData<QuoteList>
        get() = repository.quotes

    suspend fun getQuotes(category: String,page: Int) {
        // Use the repository to fetch data from the Pixabay API
        repository.getQuotes(category,1)
    }

    val favoriteQuotes: LiveData<List<FavoriteHit>> = repository.favoriteQuotes

    suspend fun insertFavorite(favorite: FavoriteHit) {
        favoriteDao.insertFavorite(favorite)
    }

    suspend fun getAllFavorites() {
        // Retrieve all favorite images from the database
        val favorites = favoriteDao.getAllFavorites()
        // Handle the retrieved data as needed
    }
}
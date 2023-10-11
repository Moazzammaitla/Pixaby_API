package com.example.pxabay_api_project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(
    private val repository: QuoteRepository,
    private val favoriteDao: FavoriteDao // Add this parameter
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository, favoriteDao) as T
    }
}
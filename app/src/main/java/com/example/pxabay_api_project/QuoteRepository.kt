package com.example.pxabay_api_project
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuoteRepository(
    private val quoteService: QuoteService,
    val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {

    private val quotesLiveData = MutableLiveData<QuoteList>()

    val quotes: LiveData<QuoteList>
        get() = quotesLiveData

    suspend fun getQuotes(page: Int) {

        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            val result = quoteService.getQuotes(page)
            if (result?.body() != null) {
                quoteDatabase.quoteDao().addQuotes(result.body()!!.hits)
                quotesLiveData.postValue(result.body())
            }
        } else {
            val quotes = quoteDatabase.quoteDao().getQuotes()
            val quoteList = QuoteList(quotes, 1, 1)
            quotesLiveData.postValue(quoteList)
        }
    }

    val favoriteQuotes: LiveData<List<FavoriteHit>> = quoteDatabase.favoriteDao().getAllFavorites()

    suspend fun insertFavorite(favorite: FavoriteHit) {
        quoteDatabase.favoriteDao().insertFavorite(favorite)
    }
}
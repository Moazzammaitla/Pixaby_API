package com.example.pxabay_api_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var quoteAdapter: QuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = (application as QuoteApplication).quoteRepository
        val favoriteDao = repository.quoteDatabase.favoriteDao() // Retrieve the favoriteDao

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository, favoriteDao)).get(MainViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        quoteAdapter = QuoteAdapter(this,emptyList()) { position, isFavorite ->
            onFavoriteClick(position, isFavorite)
        }
        recyclerView.adapter = quoteAdapter

        mainViewModel.quotes.observe(this, Observer {
            quoteAdapter.updateData(it.hits)
        })

        // Trigger the API data retrieval using a coroutine
        GlobalScope.launch(Dispatchers.Main) {
            try {
                mainViewModel.getQuotes(page = 1)
            } catch (e: Exception) {
                // Handle exceptions if needed
                e.printStackTrace()
            }
        }
    }

    private fun onFavoriteClick(position: Any, isFavorite: Any?) {
        val quote = quoteAdapter.getItemAtPosition(position)
        GlobalScope.launch(Dispatchers.Main) {
            if (isFavorite as? Boolean == true) {

                Toast.makeText(this@MainActivity,"Favorite",Toast.LENGTH_LONG).show()
                // Insert the favorite image into the database
                val favoriteHit = FavoriteHit(
                    0,
                    imageId = quote.id, // Adjust this based on your API response
                    largeImageURL = quote.largeImageURL,
                    likes = quote.likes,
                    comments = quote.comments
                    // Add other properties as needed
                )
                mainViewModel.insertFavorite(favoriteHit)

            } else {

                // Update the favorite button's icon
//                quote.isFavorite = true
//                quoteAdapter.notifyItemChanged(position)
            }
        }
    }
}
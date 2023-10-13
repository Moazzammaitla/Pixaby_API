package com.example.pxabay_api_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class NewActivity : AppCompatActivity() {

    private lateinit var favoriteRecyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        favoriteRecyclerView = findViewById(R.id.favoriteRecyclerView)
        favoriteRecyclerView.layoutManager = LinearLayoutManager(this)
        favoriteAdapter = FavoriteAdapter(emptyList()) { favoriteItem ->
            // Handle favorite item removal
            removeFavorite(favoriteItem)
        }
        favoriteRecyclerView.adapter = favoriteAdapter

        val repository = (application as QuoteApplication).quoteRepository

        // Observe changes in the favorite items and update the adapter
        repository.favoriteQuotes.observe(this, Observer {
            favoriteAdapter.updateData(it)
        })
    }

    private fun removeFavorite(favoriteItem: FavoriteHit) {
        val repository = (application as QuoteApplication).quoteRepository

        // Call the removeFavorite function within a coroutine
        lifecycleScope.launch {
            repository.removeFavorite(favoriteItem)
        }
    }
}
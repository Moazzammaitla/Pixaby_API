package com.example.pxabay_api_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity: AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var quoteAdapter: QuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = (application as QuoteApplication).quoteRepository
        val favoriteDao = repository.quoteDatabase.favoriteDao()

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            val intent = Intent(this, NewActivity::class.java)
            startActivity(intent)
        }

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository, favoriteDao)).get(MainViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        quoteAdapter = QuoteAdapter(this, emptyList()) { position, isFavorite ->
            onFavoriteClick(position, isFavorite)
        }
        recyclerView.adapter = quoteAdapter

        // Find the category buttons
        val backgroundsButton = findViewById<Button>(R.id.backgroundsButton)
        val fashionButton = findViewById<Button>(R.id.fashionButton)
        val natureButton = findViewById<Button>(R.id.natureButton)
        val scienceButton = findViewById<Button>(R.id.scienceButton)
        val travelButton = findViewById<Button>(R.id.travelButton)
        val animalsButton = findViewById<Button>(R.id.animalsButton)
        val foodButton = findViewById<Button>(R.id.foodButton)

        // Set click listeners for category buttons
        backgroundsButton.setOnClickListener {
            loadCategory("backgrounds")
            changeButtonColor(backgroundsButton)
        }

        fashionButton.setOnClickListener {
            loadCategory("fashion")
            changeButtonColor(fashionButton)
        }

        natureButton.setOnClickListener {
            loadCategory("nature")
            changeButtonColor(natureButton)
        }

        scienceButton.setOnClickListener {
            loadCategory("science")
            changeButtonColor(scienceButton)
        }

        travelButton.setOnClickListener {
            loadCategory("travel")
            changeButtonColor(travelButton)
        }

        animalsButton.setOnClickListener {
            loadCategory("animals")
            changeButtonColor(animalsButton)
        }

        foodButton.setOnClickListener {
            loadCategory("food")
            changeButtonColor(foodButton)
        }

        mainViewModel.quotes.observe(this, Observer {
            quoteAdapter.updateData(it.hits)
        })

        GlobalScope.launch(Dispatchers.Main) {
            try {
                // Load the default category (e.g., "backgrounds")
                mainViewModel.getQuotes(page = 1, category = "backgrounds")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun changeButtonColor(button: Button) {
        // Reset the background color of all buttons to the default color
        val defaultColor = getColor(com.google.android.material.R.color.design_default_color_primary)
        val buttons = listOf<Button>(
            findViewById(R.id.backgroundsButton),
            findViewById(R.id.fashionButton),
            findViewById(R.id.natureButton),
            findViewById(R.id.scienceButton),
            findViewById(R.id.travelButton),
            findViewById(R.id.animalsButton),
            findViewById(R.id.foodButton)
        )
        buttons.forEach { it.setBackgroundColor(defaultColor) }

        // Change the background color of the clicked button
        val selectedColor = getColor(com.google.android.material.R.color.m3_chip_text_color)
        button.setBackgroundColor(selectedColor)
    }

    private fun onFavoriteClick(position: Int, isFavorite: Boolean) {
        val quote = quoteAdapter.getItemAtPosition(position)
        GlobalScope.launch(Dispatchers.Main) {
            if (isFavorite) {
                Toast.makeText(this@MainActivity, "Favorite", Toast.LENGTH_LONG).show()
                val favoriteHit = FavoriteHit(
                    0,
                    imageId = quote.id,
                    largeImageURL = quote.largeImageURL,
                    likes = quote.likes,
                    comments = quote.comments
                )
                mainViewModel.insertFavorite(favoriteHit)
            }
        }
    }

    private fun loadCategory(category: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                mainViewModel.getQuotes(page = 1, category = category)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
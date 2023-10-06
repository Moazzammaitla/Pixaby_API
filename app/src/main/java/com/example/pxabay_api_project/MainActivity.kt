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

class MainActivity : AppCompatActivity()  {

    lateinit var mainViewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var quoteAdapter: QuoteAdapter // Create an adapter for your RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = (application as QuoteApplication).quoteRepository

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        quoteAdapter = QuoteAdapter(emptyList()) // Pass an empty list initially
        recyclerView.adapter = quoteAdapter

        // Observe changes in the ViewModel's quotes LiveData
        mainViewModel.quotes.observe(this, Observer {
            // Update the RecyclerView adapter with the new data
            quoteAdapter.updateData(it.hits)

            // Display a toast with the number of items in the list
//            Toast.makeText(this@MainActivity, "Loaded ${it.hits.size} items", Toast.LENGTH_SHORT).show()
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
}
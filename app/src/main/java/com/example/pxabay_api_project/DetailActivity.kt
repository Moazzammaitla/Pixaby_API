package com.example.pxabay_api_project

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val detailImageView = findViewById<ImageView>(R.id.detailImageView)
        val detailLikesTextView = findViewById<TextView>(R.id.detailLikesTextView)
        val detailCommentsTextView = findViewById<TextView>(R.id.detailCommentsTextView)
        val detailDownloadTextView = findViewById<TextView>(R.id.detailDownloadTextView)

        // Get the image URL, likes, comments, tags, and download details from the intent
        val imageURL = intent.getStringExtra("imageURL")
        val likes = intent.getIntExtra("likes", 0)
        val comments = intent.getIntExtra("comments", 0)
        val downloads = intent.getIntExtra("downloads", 0)

        // Load and display the image in the ImageView
        Glide.with(this)
            .load(imageURL)
            .into(detailImageView)

        // Display the likes and comments
        detailLikesTextView.text = "Likes: $likes"
        detailCommentsTextView.text = "Comments: $comments"


        // Display the download details
        detailDownloadTextView.text = "Download Details: $downloads"
    }
}
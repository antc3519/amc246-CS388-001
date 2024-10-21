package com.codepath.articlesearch

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var backdropImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var airTextView: TextView
    private lateinit var abstractTextView: TextView
    private lateinit var popularityTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO: Find the views for the screen
        backdropImageView = findViewById(R.id.backdropImage)
        titleTextView = findViewById(R.id.mediaTitle)
        airTextView = findViewById(R.id.mediaAir)
        abstractTextView = findViewById(R.id.mediaOverview)
        popularityTextView = findViewById(R.id.mediaPopularity)

        // TODO: Get the extra from the Intent
        val article = intent.getSerializableExtra(ARTICLE_EXTRA) as Show

        // TODO: Set the title, byline, and abstract information from the article
        titleTextView.text = article.title
        airTextView.text = "Air date: " + article.airdate
        abstractTextView.text = article.overview
        popularityTextView.text = "Popularity Score: " + article.popularity

        // TODO: Load the media image
        Glide.with(this)
            .load(article.backdropUrl)
            .centerCrop()
            .transform(RoundedCorners(30))
            .into(backdropImageView)
    }
}
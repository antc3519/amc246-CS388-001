package com.codepath.playingmovielist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codepath.playingmovielist.R.id

/**
 * The MainActivity for the BestSellerList app.
 * Launches a [PlayingMovieFragment].
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val supportFragmentManager = supportFragmentManager
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(id.content, PlayingMovieFragment(), null).commit()
    }
}
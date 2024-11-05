package com.codepath.articlesearch

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

private const val TAG = "MainActivity/"

class MainActivity : AppCompatActivity() {
    private val articles = mutableListOf<DisplayArticle>()
    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private var articleAdapter = ArticleAdapter(this,articles)
    private lateinit var entryButton: Button
    private lateinit var submitButton: Button
    private lateinit var notes: EditText
    private lateinit var formView: ConstraintLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        articlesRecyclerView = findViewById(R.id.articles)
        articlesRecyclerView.adapter = articleAdapter
        entryButton = findViewById(R.id.button)
        submitButton = findViewById(R.id.submit)
        var sleepHours = findViewById<SeekBar>(R.id.sleepTotal)
        notes = findViewById(R.id.notes)
        formView = findViewById(R.id.form)


        lifecycleScope.launch {
            (application as ArticleApplication).db.articleDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayArticle(
                        entity.date,
                        entity.sleepTotal,
                        entity.sleepNotes
                    )
                }.also { mappedList ->
                    articles.clear()
                    articles.addAll(mappedList)
                    articles.add(DisplayArticle("6/6/6","6","sad"))
                    Log.e(TAG, articles.toString(), )
                    articleAdapter.notifyDataSetChanged()
                }
            }
        }

        articlesRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            articlesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        entryButton.setOnClickListener {
            formView.visibility = View.VISIBLE
            entryButton.visibility = View.GONE
        }

        submitButton.setOnClickListener {
            formView.visibility = View.GONE
            entryButton.visibility = View.VISIBLE
            val date = LocalDate.now()
            lifecycleScope.launch {
                (application as ArticleApplication).db.articleDao().insertAll(
                    listOf(
                        ArticleEntity(
                            0,
                            date.toString(),
                            sleepHours.progress.toString(),
                            notes.text.toString()
                        )
                    )
                )
            }
        }

        articleAdapter.notifyDataSetChanged()
    }
}
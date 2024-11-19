package com.codepath.articlesearch

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import java.time.LocalDate

private const val TAG = "EntryTrackerFragment"

class EntryTrackerFragment : Fragment() {

    private val articles = mutableListOf<DisplayArticle>()
    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var entryButton: Button
    private lateinit var submitButton: Button
    private lateinit var notes: EditText
    private lateinit var formView: ConstraintLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_entry_tracker, container, false)

        // Add these configurations for the recyclerView and to configure the adapter
        val layoutManager = LinearLayoutManager(context)
        articlesRecyclerView = view.findViewById(R.id.articles)
        articlesRecyclerView.layoutManager = layoutManager
        articlesRecyclerView.setHasFixedSize(true)
        articleAdapter = ArticleAdapter(view.context, articles)
        articlesRecyclerView.adapter = articleAdapter
        entryButton = view.findViewById(R.id.button)
        submitButton = view.findViewById(R.id.submit)
        var sleepHours = view.findViewById<SeekBar>(R.id.sleepTotal)
        notes = view.findViewById(R.id.notes)
        formView = view.findViewById(R.id.form)

        lifecycleScope.launch {
            (super.getActivity()?.application as ArticleApplication).db.articleDao().getAll()
                .collect { databaseList ->
                    databaseList.map { entity ->
                        DisplayArticle(
                            entity.date,
                            entity.sleepTotal,
                            entity.sleepNotes
                        )
                    }.also { mappedList ->
                        articles.clear()
                        articles.addAll(mappedList)
                        Log.e(TAG, articles.toString(),)
                        articleAdapter.notifyDataSetChanged()
                    }
                }
        }

        articlesRecyclerView.layoutManager = LinearLayoutManager(view.context).also {
            val dividerItemDecoration = DividerItemDecoration(view.context, it.orientation)
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
                (super.getActivity()?.application as ArticleApplication).db.articleDao().insertAll(
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

        // Update the return statement to return the inflated view from above
        return view
    }

    companion object {
        fun newInstance(): EntryTrackerFragment {
            return EntryTrackerFragment()
        }
    }
}
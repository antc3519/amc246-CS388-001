package com.codepath.articlesearch

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch

private const val TAG = "EntryTrackerFragment"

class DashboardFragment : Fragment() {
    private val articles = mutableListOf<DisplayArticle>()
    private lateinit var average: TextView
    private lateinit var minimum: TextView
    private lateinit var maximum: TextView
    private lateinit var clearData: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Add these configurations for the recyclerView and to configure the adapter
        average = view.findViewById(R.id.average)
        minimum = view.findViewById(R.id.minimum)
        maximum = view.findViewById(R.id.maximum)
        clearData = view.findViewById(R.id.clearData)

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

                        val sleepData = articles.map { item -> item.sleepTotal?.toInt() ?: 0 }
                        var avr = sleepData.average()
                        var sleepEntries = sleepData.size
                        var min = sleepData.minOrNull()
                        var max = sleepData.maxOrNull()
                        if (min == null || max == null) {
                            min = 0
                            max = 0
                            avr = 0.0
                        }
                        average.text = "Average Sleep: " + avr + " hours!"
                        minimum.text = "Minimum Sleep: " + min + " hours!"
                        maximum.text = "Maximum Sleep: " + max + " hours!"
                    }
                }

        }




        clearData.setOnClickListener {
            (super.getActivity()?.application as ArticleApplication).db.articleDao().deleteAll()
            Toast.makeText(view.context, "DATA HAS BEEN CLEARED", Toast.LENGTH_SHORT).show()
        }
        
        return view
    }

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
}
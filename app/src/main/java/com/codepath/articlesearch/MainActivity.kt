package com.codepath.articlesearch

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.BuildConfig
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.view.View
import android.widget.SearchView


fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val ARTICLE_SEARCH_URL =
    "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=vfW7LI4DaQqFGbsoIL9LX85SGGtM3qPP"

class MainActivity : AppCompatActivity() {
    private val articles = mutableListOf<DisplayArticle>()
    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private var isConnected = true // Initially assume connected
    private lateinit var offlineView: View // Custom offline view
    private lateinit var searchView: SearchView
    private var filteredList = mutableListOf<DisplayArticle>()
    private var articleAdapter = ArticleAdapter(this,filteredList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        searchView = findViewById(R.id.searchView)
        articlesRecyclerView = findViewById(R.id.articles)
        articlesRecyclerView.adapter = articleAdapter

        offlineView = findViewById(R.id.offlineView)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        registerNetworkCallback()

        lifecycleScope.launch {
            (application as ArticleApplication).db.articleDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayArticle(
                        entity.headline,
                        entity.articleAbstract,
                        entity.byline,
                        entity.mediaImageUrl
                    )
                }.also { mappedList ->
                    articles.clear()
                    articles.addAll(mappedList)
                    filteredList.addAll(articles)
                    articleAdapter.notifyDataSetChanged()
                }
            }
        }

        articlesRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            articlesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        // Set up SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            // Trigger network call to fetch new data
            fetchData()
        }
        fetchData()
        filter("")
        articleAdapter.notifyDataSetChanged()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    filter(newText)
                }
                return true
            }
        })
    }

    private fun filter(newText: String?) {
        filteredList.clear()
        if (newText != null) {
            if (newText.isEmpty()) {
                filteredList.addAll(articles)
            } else {
                // Filter original list based on query
                filteredList.addAll(articles.filter { item ->
                    newText.let { item.headline?.contains(it, ignoreCase = true) } ?: false
                })
            }
        }
        articleAdapter.notifyDataSetChanged()
    }
    private fun registerNetworkCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                runOnUiThread {
                    isConnected = true
                    offlineView.visibility = View.GONE // Hide offline view
                    fetchData() // Fetch data again when online
                }
            }

            override fun onLost(network: Network) {
                runOnUiThread {
                    isConnected = false
                    offlineView.visibility = View.VISIBLE // Show offline view
                }
            }
        }
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun fetchData() {
        if(isConnected){
            val client = AsyncHttpClient()
            client.get(ARTICLE_SEARCH_URL, object : JsonHttpResponseHandler() {
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    Log.e(TAG, "Failed to fetch articles: $statusCode")
                }

                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                    Log.i(TAG, "Successfully fetched articles: $json")
                    try {

                        // TODO: Create the parsedJSON
                        val parsedJson = createJson().decodeFromString(
                            SearchNewsResponse.serializer(),
                            json.jsonObject.toString()
                        )
                        // TODO: Do something with the returned json (contains article information)
                        parsedJson.response?.docs?.let { list ->
                            lifecycleScope.launch(IO) {
                                (application as ArticleApplication).db.articleDao().deleteAll()
                                (application as ArticleApplication).db.articleDao().insertAll(list.map {
                                    ArticleEntity(
                                        headline = it.headline?.main,
                                        articleAbstract = it.abstract,
                                        byline = it.byline?.original,
                                        mediaImageUrl = it.mediaImageUrl
                                    )
                                })
                            }
                        }
                        // TODO: Save the articles and reload the screen

                    } catch (e: JSONException) {
                        Log.e(TAG, "Exception: $e")
                    }
                }

            })
        }
        swipeRefreshLayout.postDelayed({
            // Update UI with new data
            swipeRefreshLayout.isRefreshing = false // Stop refresh indicator
        }, 2000)
    }
}
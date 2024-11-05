package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

const val ARTICLE_EXTRA = "ARTICLE_EXTRA"
private const val TAG = "ArticleAdapter"

class ArticleAdapter(private val context: Context, private val articles: List<DisplayArticle>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount() = articles.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

            private val sleepTotal = itemView.findViewById<TextView>(R.id.sleepHours)
            private val dateView = itemView.findViewById<TextView>(R.id.mediaTitle)
            private val sleepNotes = itemView.findViewById<TextView>(R.id.mediaAbstract)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(article: DisplayArticle) {
            dateView.text = article.date
            sleepNotes.text = article.sleepNotes
            sleepTotal.text = article.sleepTotal + " hours"
        }

        override fun onClick(p0: View?) {
            return
        }
    }
}
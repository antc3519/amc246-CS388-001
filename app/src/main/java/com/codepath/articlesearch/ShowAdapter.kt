package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

const val ARTICLE_EXTRA = "ARTICLE_EXTRA"
private const val TAG = "ArticleAdapter"

class ArticleAdapter(private val context: Context, private val shows: List<Show>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_show, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val show = shows[position]
        holder.bind(show)
    }

    override fun getItemCount() = shows.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val mediaImageView = itemView.findViewById<ImageView>(R.id.mediaImage)
        private val titleTextView = itemView.findViewById<TextView>(R.id.mediaTitle)
        private val airDateTextView = itemView.findViewById<TextView>(R.id.mediaAir)
        private val abstractTextView = itemView.findViewById<TextView>(R.id.mediaOverview)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(show: Show) {
            titleTextView.text = show.title
            abstractTextView.text = show.overview
            airDateTextView.text = show.airdate

            Glide.with(context)
                .load(show.mediaImageUrl)
                .centerCrop()
                .transform(RoundedCorners(30))
                .into(mediaImageView)
        }

        override fun onClick(v: View?) {
            // TODO: Get selected article
            val show = shows[absoluteAdapterPosition]
            // TODO: Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ARTICLE_EXTRA, show)
            context.startActivity(intent)
        }
    }
}
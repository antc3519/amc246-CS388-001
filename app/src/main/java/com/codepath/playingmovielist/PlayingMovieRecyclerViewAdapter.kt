package com.codepath.playingmovielist

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.playingmovielist.R.id

/**
 * [RecyclerView.Adapter] that can display a [PlayingMovie] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class PlayingMovieRecyclerViewAdapter(
    private val movies: List<PlayingMovie>,
    private val mListener: OnListFragmentInteractionListener?
    )
    : RecyclerView.Adapter<PlayingMovieRecyclerViewAdapter.MovieViewHolder>()
    {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_playing_movie, parent, false)
        return MovieViewHolder(view)
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: PlayingMovie? = null
        val mMovieTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView
        val mMovieImage: ImageView = mView.findViewById<View>(id.movie_image) as ImageView
        val mMovieDescription: TextView = mView.findViewById<View>(id.movie_description) as TextView

        override fun toString(): String {
            return mMovieTitle.toString()
        }
    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        holder.mMovieTitle.text = movie.title
        holder.mMovieDescription.text = movie.overview
        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500" + movie.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(holder.mMovieImage)
    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return movies.size
    }
}
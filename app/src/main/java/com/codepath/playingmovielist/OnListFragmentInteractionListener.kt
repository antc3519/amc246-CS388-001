package com.codepath.playingmovielist

/**
 * This interface is used by the [PlayingMovieRecyclerViewAdapter] to ensure
 * it has an appropriate Listener.
 *
 * In this app, it's implemented by [PlayingMovieFragment]
 */
interface OnListFragmentInteractionListener {
    fun onItemClick(item: PlayingMovie)
}

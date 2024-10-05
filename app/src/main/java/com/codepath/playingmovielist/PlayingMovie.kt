package com.codepath.playingmovielist

import com.google.gson.annotations.SerializedName

/**
 * The Model for storing a single book from the NY Times API
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the gson library.
 */
class PlayingMovie {

    @JvmField
    @SerializedName("original_title")
    var title: String? = null

    @JvmField
    @SerializedName("overview")
    var overview: String? = null

    @SerializedName("poster_path")
    var imageUrl: String? = null
}
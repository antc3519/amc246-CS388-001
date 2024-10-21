package com.codepath.articlesearch
import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SearchNewsResponse(
    @SerialName("results")
    val response: List<Show>?
)

@Keep
@Serializable
data class Show(
    @SerialName("overview")
    val overview: String?,
    @SerialName("first_air_date")
    val airdate: String?,
    @SerialName("name")
    val title: String?,
    @SerialName("popularity")
    val popularity: String?,
    @SerialName("backdrop_path")
    val backdrop: String?,
    @SerialName("poster_path")
    val multimedia: String?,

)

: java.io.Serializable {
    val mediaImageUrl = "https://image.tmdb.org/t/p/w500/${multimedia}"
    val backdropUrl = "https://image.tmdb.org/t/p/w500/${backdrop}"
}
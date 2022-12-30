package com.rf.streamfinder.scraping

import kotlinx.serialization.Serializable

@Serializable
data class ScrapedMediaData(
    val title:String? = null,
    val releaseDate:String? = null,
    val imdbRating:String? = null,
    val streamingList:List<StreamingPlatform> = listOf()
)

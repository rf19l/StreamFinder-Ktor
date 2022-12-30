package com.rf.streamfinder.scraping

import kotlinx.serialization.Serializable

@Serializable
data class StreamingPlatform(
    val platformName: String? = null,
    val seasonCount: String? = null,
    val image: String? = null,
)

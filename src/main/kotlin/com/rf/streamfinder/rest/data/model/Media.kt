package com.rf.streamfinder.rest.data.model

import com.rf.streamfinder.scraping.StreamingPlatform
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

/* todo
    add add images
    add streaming services available enum
 */
@Serializable
data class Media(
    val title: String? = null,
    val releaseDate: String? = null,
    val imdbRating: String? = null,
    val streamingList: List<StreamingPlatform> = listOf(),
    @BsonId
    val id: String = ObjectId().toString()
)

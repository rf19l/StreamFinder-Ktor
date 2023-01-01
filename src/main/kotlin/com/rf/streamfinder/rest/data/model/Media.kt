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
    var title: String? = null,
    var releaseYear: Int? = null,
    var imdbRating: Double? = null,
    var streamingList: List<StreamingPlatform>? = listOf(),
    var purchaseList: List<StreamingPlatform>? = listOf(),
    @BsonId
    var id: String = ObjectId().toString(),
)

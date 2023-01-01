package com.rf.streamfinder.scraping

import com.fasterxml.jackson.annotation.JsonValue
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class StreamingPlatform(
    var platformName: String? = null,
    var seasonCount: Int? = null,
    var image: String? = null,
    var isHdAvailable: Boolean = false,
    var platformType: PlatformType? = null,
    @BsonId
    var id: String = ObjectId().toString(),
)

enum class PlatformType(@JsonValue val typeName: String) {
    STREAM("Stream"),
    PURCHASE("Purchase")
}

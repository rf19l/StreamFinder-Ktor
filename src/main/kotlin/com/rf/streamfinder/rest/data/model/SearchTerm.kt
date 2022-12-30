package com.rf.streamfinder.rest.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class SearchTerm(
    val queryString: String,
    @BsonId
    val id: String = ObjectId().toString()
)

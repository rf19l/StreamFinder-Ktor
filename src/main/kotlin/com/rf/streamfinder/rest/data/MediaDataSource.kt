package com.rf.streamfinder.rest.data

import com.rf.streamfinder.rest.data.model.Media

interface MediaDataSource {
    suspend fun searchForMediaFromWeb():List<Media>

    suspend fun getMediaFromDataBase():List<Media>
    suspend fun searchForMedia(query:String):List<Media>

    suspend fun getMediaFromWeb(query:String):List<Media>
    suspend fun isPreviousSearch(query:String):Boolean

}
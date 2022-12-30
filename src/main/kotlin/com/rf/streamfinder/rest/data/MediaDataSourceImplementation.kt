package com.rf.streamfinder.rest.data

import com.rf.streamfinder.rest.data.model.Media
import com.rf.streamfinder.rest.data.model.SearchTerm
import com.rf.streamfinder.scraping.ScrapeJustWatch
import org.litote.kmongo.coroutine.CoroutineDatabase

class MediaDataSourceImplementation(
    private val db: CoroutineDatabase
) : MediaDataSource {

    private val mediaObjects = db.getCollection<Media>()
    private val previousSearchTerms = db.getCollection<SearchTerm>()
    override suspend fun searchForMediaFromWeb(): List<Media> {
        return listOf()
        //TODO
    }

    override suspend fun getMediaFromDataBase(): List<Media> {
        return mediaObjects.find().toList()
    }

    //TODO fix boolean to check database
    override suspend fun searchForMedia(query: String): List<Media> {
        return if (false && (isPreviousSearch(query))) getMediaFromDataBase() else getMediaFromWeb(query)
    }

    override suspend fun getMediaFromWeb(query: String): List<Media> {
        return ScrapeJustWatch(query)
    }

    override suspend fun isPreviousSearch(query: String): Boolean {
        return previousSearchTerms.find(query).toList().isNotEmpty()
    }
}
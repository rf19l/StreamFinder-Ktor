package com.rf.streamfinder.scraping

import com.rf.streamfinder.rest.data.model.Media
import io.ktor.server.application.*
import it.skrape.core.document
import it.skrape.core.htmlDocument
import it.skrape.fetcher.*
import it.skrape.fetcher.extractIt
import it.skrape.selects.Doc
import it.skrape.selects.eachLink
import it.skrape.selects.html5.a
import org.jsoup.select.Elements

/**
 * I'm worried lazy loading may cause issues getting all results from the fetcher.
 * May need to use Jsoup if this ends up being an issue
 */
suspend fun ScrapeJustWatch(query: String): List<Media> {
    skrape(BrowserFetcher) {
        request { url = "https://www.justwatch.com/us/search?q=$query" }
        extract{
            htmlDocument{println(this)}
            println(this)
        }
    }

    return listOf()
}

fun getData(elements: Elements): List<Media> {

    return listOf()
}
package com.rf.streamfinder.scraping

import com.rf.streamfinder.rest.data.model.Media
import it.skrape.core.document
import it.skrape.core.fetcher.HttpFetcher
import it.skrape.core.fetcher.Method
import it.skrape.core.htmlDocument
import it.skrape.extract
import it.skrape.selects.DocElement
import it.skrape.selects.html5.body
import it.skrape.selects.html5.div
import it.skrape.skrape
import javax.lang.model.util.Elements

private const val streamingGridDivClassName = "price-comparison__grid__row--inline[data-v-660b374a]"


/**
 * I'm worried lazy loading may cause issues getting all results from the fetcher.
 * May need to use Jsoup if this ends up being an issue
 */
suspend fun ScrapeJustWatch(query: String): List<Media> {
    skrape(HttpFetcher) {
        request {
            url = "https://www.justwatch.com/us/search?q=$query"
            method = Method.GET


        }
        extract {
            htmlDocument {
                body {
                    div {
                        withClass = "title-list-row__row"
                        val rows = findAll { this }
                        handleRowData(rows)

                    }
                }
            }
            val a = this.document
            val b = this.responseBody
            val c = this.responseStatus
            println("content type is ${this.contentType}")

        }
    }


    return listOf()
}

private fun handleRowData(rows: List<DocElement>): List<Media> {
    return rows.map { transformHtmlRow(it) }.toList()
}

private fun transformHtmlRow(row: DocElement): Media {
    val pictures = row.eachImage
    val links = row.eachLink
    val hRefs = row.eachHref
    val sRcS = row.eachSrc
    val monetizationGrid: DocElement? = row.div {
        withClass = "monetizations"
        findFirst {
            this
        }
    }
    monetizationGrid?.let {
        val streamingGrids = it.div {
            withClass = "price-comparison__grid__row--inline[data-v-660b374a]"
            findAll { this }
        }
    }
    println("reached")
    return Media()
}

fun getData(elements: Elements): List<Media> {

    return listOf()
}
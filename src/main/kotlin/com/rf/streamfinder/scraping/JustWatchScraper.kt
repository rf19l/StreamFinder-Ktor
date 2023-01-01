package com.rf.streamfinder.scraping

import com.rf.streamfinder.rest.data.model.Media
import com.rf.streamfinder.scraping.PlatformType.PURCHASE
import com.rf.streamfinder.scraping.PlatformType.STREAM
import it.skrape.core.fetcher.HttpFetcher
import it.skrape.core.fetcher.Method
import it.skrape.core.htmlDocument
import it.skrape.extract
import it.skrape.selects.DocElement
import it.skrape.selects.html5.*
import it.skrape.skrape

private const val streamingGridDivClassName = "price-comparison__grid__row--inline[data-v-660b374a]"
private const val justWatchStreamingUrl = "https://www.justwatch.com/us/search?q="
private const val showRowClassName = "title-list-row__row"
private const val TitleSpanClassName = "header-title"
private const val YearSpanClassName = "header-year"
private const val platformIconClassName = "provider-icon"
private const val gridItemTextDivClassName = "price-comparison__grid__row__price"
private const val scoreDivClassName = "jw-scoring-listing__rating"
private const val imdbAttributeKey = "v-uib-tooltip"
private const val imdbAttributeValue = "IMDB"


/**
 * I'm worried lazy loading may cause issues getting all results from the fetcher.
 * May need to use Jsoup if this ends up being an issue
 */
suspend fun ScrapeJustWatch(query: String): List<Media> {
    return skrape(HttpFetcher) {
        request {
            url = justWatchStreamingUrl + query
            method = Method.GET


        }
        extract {
            htmlDocument {
                relaxed = true
                body {
                    div {
                        withClass = showRowClassName
                        val rows = findAll { this }
                        handleRowData(rows)

                    }
                }
            }
        }
    }
}

private fun handleRowData(rows: List<DocElement>): List<Media> {
    return rows.map { transformHtmlRow(it) }.toList()
}


private fun transformHtmlRow(row: DocElement): Media {
    val media = Media()
    val monetizationGrids = row.div {
        withClass = streamingGridDivClassName
        findAll { this }
    }
    row.span {
        withClass = TitleSpanClassName
        media.title = findFirst { this }.ownText
        withClass = YearSpanClassName
        media.releaseYear = findFirst { this }.ownText.removeParenthesis().toIntOrNull()
    }
    media.imdbRating = row.div {
        withClass = scoreDivClassName
        withAttribute = Pair(imdbAttributeKey, imdbAttributeValue)
        findFirst { this }.allElements.firstOrNull{it.tagName == "a"}?.ownText?.toDoubleOrNull()
    }
    media.streamingList = monetizationGrids.find { it.attributes.values.any { it.contains("--stream") } }?.let {
        handlePlatformsRow(it, STREAM)
    }
    media.purchaseList = monetizationGrids.find { it.attributes.values.any { it.contains("--buy") } }?.let {
        handlePlatformsRow(it, PURCHASE)
    }

    return media
}

private const val gridElementDivClassName = "price-comparison__grid__row__element"

private fun handlePlatformsRow(row: DocElement, type: PlatformType): List<StreamingPlatform> {
    return row.div {
        withClass = gridElementDivClassName
        findAll { this }
    }.map { handleRowEntry(it, type) }
}


private fun handleRowEntry(entry: DocElement, type: PlatformType): StreamingPlatform {
    val platform = StreamingPlatform(platformType = type)
    val pattern = "data-srcset=\"(.+?)\"".toRegex()
    entry.picture {
        withClass = platformIconClassName
        findFirst { this }.html.let {
            platform.image = pattern.find(it)?.groupValues?.lastOrNull()
            platform.platformName = extractTitle(it)
        }

    }

    val platformText = entry.div {
        withClass = gridItemTextDivClassName
        findFirst { this }.span {
            findAll { this }.map { it.ownText }
        }
    }
    platform.isHdAvailable = platformText.any { it.contains("hd", true) }
    platform.seasonCount = platformText.map { it.filter { it.isDigit() } }.firstOrNull()?.toIntOrNull()
    return platform
}

private fun String.removeParenthesis(): String {
    return this.removePrefix("(").removeSuffix(")")
}

private fun extractTitle(input: String): String? {
    val pattern = "title=\"(.+?)\"".toRegex()
    val match = pattern.find(input)
    return if (match != null) {
        match.groupValues[1]
    } else {
        null
    }
}
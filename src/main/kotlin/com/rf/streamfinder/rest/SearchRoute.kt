package com.rf.streamfinder.rest

import com.rf.streamfinder.rest.data.MediaDataSource
import com.rf.streamfinder.rest.data.MediaDataSourceImplementation
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun Routing.searchGet() {
    get("/search") {
        val query: String? = call.request.queryParameters["title"]
        if (query == null) {
            call.respondText(
                text = "search term cannot be empty",
                contentType = ContentType.Text.Plain,
                status = HttpStatusCode.BadRequest
            )
        }
        val mediaDataSource: MediaDataSource = object : KoinComponent {
            val mediaSource: MediaDataSource by inject()
        }.mediaSource
        query?.let {
            call.respond(mediaDataSource.searchForMedia(it))
            return@get
        }
        call.respondText(
            text = "invalid search entry",
            status = HttpStatusCode.BadRequest,
            contentType = ContentType.Text.Plain
        )
    }

}
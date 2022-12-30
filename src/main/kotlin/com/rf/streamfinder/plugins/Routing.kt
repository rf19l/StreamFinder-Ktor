package com.rf.streamfinder.plugins

import com.rf.streamfinder.rest.searchGet
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText(
                text = "hello", contentType = ContentType.Text.Plain, status = HttpStatusCode.Accepted
            )
        }
        post("/") {
            val post = call.receive<String>()
            call.respondText(text = "received $post from post body", contentType = ContentType.Text.Plain)
        }
        this.searchGet()
    }
}

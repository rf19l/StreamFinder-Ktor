package com.rf.streamfinder

import com.rf.streamfinder.rest.di.mainModule
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.rf.streamfinder.plugins.*
import org.koin.core.context.startKoin

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    startKoin{
        modules(mainModule)
    }
    configureSerialization()
    configureRouting()
}

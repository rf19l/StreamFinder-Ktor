package com.rf.streamfinder.rest.di

import com.rf.streamfinder.rest.data.MediaDataSource
import com.rf.streamfinder.rest.data.MediaDataSourceImplementation
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase("media_db")
    }
    single<MediaDataSource> {
        MediaDataSourceImplementation(get())
    }
}
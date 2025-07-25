package br.com.gservices.rickandmortyv2.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.minutes

val httpClient = HttpClient {
    defaultRequest {
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
        url {
            host = "rickandmortyapi.com"
            protocol = URLProtocol.HTTPS
        }
    }

    install(ContentNegotiation) {
        json(
            Json {
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 2.minutes.inWholeMilliseconds
        connectTimeoutMillis = 1.minutes.inWholeMilliseconds
    }

    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                println(message = message)
            }
        }
    }
}
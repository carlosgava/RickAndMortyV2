package br.com.gservices.rickandmortyv2.network

import br.com.gservices.rickandmortyv2.models.BaseModel
import br.com.gservices.rickandmortyv2.models.Characters
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class HttpService: HttpInterface {
    override suspend fun getAllCharacters(page: Int): BaseModel<Characters> {
        return httpClient.get {
            url {
                appendPathSegments(
                    segments = listOf(
                        "api",
                        "character",
                    ),
                    encodeSlash = true
                )
                parameters.append("page", page.toString())
            }
        }.body()
    }

    override suspend fun getCharacterById(characterId: Long): Characters {
        return httpClient.get {
            url {
                appendPathSegments(
                    segments = listOf(
                        "api",
                        "character",
                        characterId.toString()
                    ),
                    encodeSlash = true
                )
            }
        }.body()
    }
}
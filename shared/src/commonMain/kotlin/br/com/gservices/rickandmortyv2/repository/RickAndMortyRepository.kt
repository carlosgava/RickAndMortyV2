package br.com.gservices.rickandmortyv2.repository

import br.com.gservices.rickandmortyv2.network.HttpRequestState
import br.com.gservices.rickandmortyv2.network.HttpService
import kotlinx.coroutines.flow.flow

class RickAndMortyRepository(private val service: HttpService) {
    fun getAllCharacters(page: Int) = flow {
        try {
            emit(HttpRequestState.Loading)
            val response = service.getAllCharacters(page)
            emit(HttpRequestState.Success(data = response))
        } catch (ex: Exception) {
            emit(HttpRequestState.Error(exception = ex))
        }
    }

    fun getCharacterById(characterId: Long) = flow {
        try {
            emit(HttpRequestState.Loading)
            val response = service.getCharacterById(characterId)
            emit(HttpRequestState.Success(data = response))
        } catch(ex: Exception) {
            emit(HttpRequestState.Error(exception = ex))
        }
    }
}
package br.com.gservices.rickandmortyv2.network

sealed class HttpRequestState<out R> {
    data class Success<out T>(val data: T) : HttpRequestState<T>()
    data class Error(val exception: Exception) : HttpRequestState<Nothing>()
    data object Loading : HttpRequestState<Nothing>()
}
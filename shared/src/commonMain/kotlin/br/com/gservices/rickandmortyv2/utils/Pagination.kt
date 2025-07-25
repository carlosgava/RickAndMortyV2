package br.com.gservices.rickandmortyv2.utils

import br.com.gservices.rickandmortyv2.network.HttpRequestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class Pagination<T>(
    private val scope: CoroutineScope,
    private val initialKey: Int = 1,
    private val incrementBy: Int = 1,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Int) -> Flow<HttpRequestState<T>>,
    private val onError: suspend (Throwable) -> Unit,
    private val onSuccess: suspend (items: T, newKey: Int) -> Unit
) {
    private var isMakingRequest = false
    private var currentKey = initialKey
    private var hasError = false

    fun reset() {
        currentKey = initialKey
        hasError = false
    }

    fun loadItems() {
        if (isMakingRequest || hasError) return

        isMakingRequest = true
        scope.launch {
            onLoadUpdated(true)

            val resultFlow = onRequest(currentKey)

            resultFlow.collect { result ->
                when (result) {
                    is HttpRequestState.Loading -> Unit
                    is HttpRequestState.Success -> {
                        val items = result.data
                        val nextKey = currentKey + incrementBy
                        onSuccess(items, nextKey)
                        currentKey = nextKey
                    }
                    is HttpRequestState.Error -> {
                        hasError = true
                        onError(result.exception)
                    }
                }
            }

            onLoadUpdated(false)
            isMakingRequest = false
        }
    }
}
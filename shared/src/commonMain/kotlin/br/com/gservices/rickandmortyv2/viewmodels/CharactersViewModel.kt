package br.com.gservices.rickandmortyv2.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gservices.rickandmortyv2.repository.RickAndMortyRepository
import br.com.gservices.rickandmortyv2.utils.Pagination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CharactersViewModel: ViewModel(), KoinComponent {
    private val repository: RickAndMortyRepository by inject()
    private val _uiState = MutableStateFlow(CharacterUiState())
    val uiState: StateFlow<CharacterUiState> get() = _uiState.asStateFlow()

    private val pagination = Pagination(
        scope = viewModelScope,
        onLoadUpdated = {
            _uiState.update { it ->
                it.copy(isLoading = true)
            }
        },
        onRequest = { page ->
            repository.getAllCharacters(page)

        },
        onError = { error ->
            _uiState.update { it ->
                it.copy(errorMessage = error.message)
            }
        },
        onSuccess = { items, _ ->
            _uiState.update { it ->
                it.copy(characters = items.results)
            }
        }
    )

    fun loadCharacters() = pagination.loadItems()
}
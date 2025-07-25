package br.com.gservices.rickandmortyv2.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gservices.rickandmortyv2.network.HttpRequestState
import br.com.gservices.rickandmortyv2.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CharacterDetailViewModel: ViewModel(), KoinComponent {
    private val repository: RickAndMortyRepository by inject()
    private val _uiState = MutableStateFlow(CharacterDetailUiState())
    val uiState: StateFlow<CharacterDetailUiState> get() = _uiState.asStateFlow()

    private fun <T> handleStateUpdate(
        result: HttpRequestState<T>,
        stateUpdater: (CharacterDetailUiState, T?) -> CharacterDetailUiState
    ) {
        _uiState.update { currentState ->
            when (result) {
                is HttpRequestState.Loading -> currentState.copy(isLoading = true)
                is HttpRequestState.Success -> stateUpdater(
                    currentState,
                    result.data
                ).copy(isLoading = false)

                is HttpRequestState.Error -> currentState.copy(
                    isLoading = false,
                    errorMessage = result.exception.message
                )
            }
        }
    }

    private suspend fun updateCharacterDetail(characterId: Long) {
        repository.getCharacterById(characterId = characterId).collect { result ->
            handleStateUpdate(result) { state, characters -> state.copy(character = characters)}
        }
    }

    fun fetchCharacterDetail(characterId: Long) {
        viewModelScope.launch {
            launch { updateCharacterDetail(characterId = characterId) }
        }
    }
}
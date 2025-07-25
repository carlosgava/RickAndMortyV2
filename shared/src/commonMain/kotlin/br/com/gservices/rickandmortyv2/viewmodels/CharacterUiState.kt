package br.com.gservices.rickandmortyv2.viewmodels

import br.com.gservices.rickandmortyv2.models.Characters

data class CharacterUiState(
    val characters: List<Characters> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

package br.com.gservices.rickandmortyv2.viewmodels

import br.com.gservices.rickandmortyv2.models.Characters

data class CharacterDetailUiState(
    val character: Characters? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


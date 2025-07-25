package br.com.gservices.rickandmortyv2.models

import kotlinx.serialization.Serializable

@Serializable
data class Characters (
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)
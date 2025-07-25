package br.com.gservices.rickandmortyv2.models

import kotlinx.serialization.Serializable

@Serializable
data class Location (
    val name: String,
    val url: String
)
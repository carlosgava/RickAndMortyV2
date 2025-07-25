package br.com.gservices.rickandmortyv2.models

import kotlinx.serialization.Serializable

@Serializable
data class BaseModel<T>(
    val info: Info,
    val results: List<T>
)

@Serializable
data class Info (
    val count: Long,
    val pages: Long,
    val next: String,
    val prev: String? = null
)
package br.com.gservices.rickandmortyv2.network

import br.com.gservices.rickandmortyv2.models.BaseModel
import br.com.gservices.rickandmortyv2.models.Characters

interface HttpInterface {
    suspend fun getAllCharacters(page: Int): BaseModel<Characters>
    suspend fun getCharacterById(characterId: Long): Characters
}
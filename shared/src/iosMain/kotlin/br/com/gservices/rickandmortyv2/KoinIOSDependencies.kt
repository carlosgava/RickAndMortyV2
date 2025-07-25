package br.com.gservices.rickandmortyv2

import br.com.gservices.rickandmortyv2.repository.RickAndMortyRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class KoinIOSDependencies: KoinComponent {
    val repository: RickAndMortyRepository by inject()
}
package br.com.gservices.rickandmortyv2.di

import br.com.gservices.rickandmortyv2.network.HttpInterface
import br.com.gservices.rickandmortyv2.network.HttpService
import br.com.gservices.rickandmortyv2.network.httpClient
import br.com.gservices.rickandmortyv2.repository.RickAndMortyRepository
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModules = module {
    single { httpClient }
    single { HttpService() }
    single {
        RickAndMortyRepository(get())
    }
}

fun initKoin() = initKoin(emptyList())

fun initKoin(extraModules: List<Module>) {
    startKoin {
        modules(
            dataModules,
            *extraModules.toTypedArray(),
        )
    }
}
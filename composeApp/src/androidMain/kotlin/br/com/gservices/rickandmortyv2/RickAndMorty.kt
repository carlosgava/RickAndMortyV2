package br.com.gservices.rickandmortyv2

import android.app.Application
import br.com.gservices.rickandmortyv2.di.initKoin
import br.com.gservices.rickandmortyv2.viewmodels.CharactersViewModel
import org.koin.dsl.module

class RickAndMorty: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            listOf(
                module {
                    factory {
                        CharactersViewModel()
                    }
                }
            )
        )
    }
}
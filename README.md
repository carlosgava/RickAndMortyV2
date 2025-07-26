Este é um projeto Kotlin Multiplataforma voltado para Android e iOS.

* `/composeApp` é para o código que será compartilhado entre seus aplicativos Compose Multiplataforma.
Ele contém várias subpastas:
- `commonMain` é para o código comum a todos os destinos.
- Outras pastas são para o código Kotlin que será compilado apenas para a plataforma indicada no nome da pasta.
Por exemplo, se você quiser usar o CoreCrypto da Apple para a parte iOS do seu aplicativo Kotlin,
`iosMain` seria a pasta certa para essas chamadas.

* `/iosApp` contém aplicativos iOS. Mesmo se você estiver compartilhando sua interface com o Compose Multiplataforma,
você precisa deste ponto de entrada para seu aplicativo iOS. É aqui também que você deve adicionar o código SwiftUI para o seu projeto.

* `/shared` é para o código que será compartilhado entre todos os destinos do projeto.
A subpasta mais importante é `commonMain`. Se preferir, você também pode adicionar código às pastas específicas da plataforma aqui.

Saiba mais sobre [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…


Principais livrarias utilizadas nesse projeto:
* `ktor` - Conectividade com a internet
* `coil` - Carregamento de imagens asincronas
* `koin` -  Injetor de dependencias
* `skie` - Compatibilizador de ViewModels entre Android e iOS
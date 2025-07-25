package br.com.gservices.rickandmortyv2

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import br.com.gservices.rickandmortyv2.ui.CharacterDetailUI
import br.com.gservices.rickandmortyv2.ui.CharactersListUI
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
object ListDestination

@Serializable
data class DetailDestination(val objectId: Long)


@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Surface {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = ListDestination) {
                composable<ListDestination> {
                    CharactersListUI(
                        navigate = { objectId ->
                            navController.navigate(DetailDestination(objectId))
                        }
                    )
                }
                composable<DetailDestination> { backStackEntry ->
                    CharacterDetailUI(
                        characterId = backStackEntry.toRoute<DetailDestination>().objectId,
                        navigateToList = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
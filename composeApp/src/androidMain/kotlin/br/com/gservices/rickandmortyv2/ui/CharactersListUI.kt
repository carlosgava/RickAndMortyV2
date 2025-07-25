package br.com.gservices.rickandmortyv2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.gservices.rickandmortyv2.viewmodels.CharactersViewModel
import br.com.gservices.rickandmortyv2.models.Characters
import coil3.compose.AsyncImage

@Composable
fun CharactersListUI(
    navigate: (objectId: Long) -> Unit
) {
    val viewModel: CharactersViewModel = viewModel<CharactersViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadCharacters()
    }

    if (!uiState.characterList.isEmpty()) {
        CharactersGrid(
            characters = uiState.characterList,
            onClick = navigate,
       )
    } else {
        EmptyScreenContent(Modifier.fillMaxSize())
    }
}


@Composable
private fun CharactersGrid(
    characters: List<Characters>,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
    ) {
        items(characters, key = { it.id }) { chars ->
            CharacterFrame(
                chars = chars,
                onClick = { onClick(chars.id) },
            )
        }
    }
}

@Composable
private fun CharacterFrame(
    chars: Characters,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = chars.image,
            contentDescription = chars.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.LightGray),
        )

        Spacer(Modifier.height(2.dp))

        Text(chars.name, style = MaterialTheme.typography.titleMedium)
        Text(chars.location.name, style = MaterialTheme.typography.bodyMedium)
        Text(chars.status, style = MaterialTheme.typography.bodySmall)
    }
}

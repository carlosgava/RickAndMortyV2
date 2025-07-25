package br.com.gservices.rickandmortyv2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import br.com.gservices.rickandmortyv2.R
import br.com.gservices.rickandmortyv2.models.Characters

import br.com.gservices.rickandmortyv2.viewmodels.CharacterDetailViewModel

@Composable
fun CharacterDetailUI(
    characterId: Long,
    navigateToList: () -> Unit
) {
    val viewModel: CharacterDetailViewModel = viewModel<CharacterDetailViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(characterId) {
        viewModel.fetchCharacterDetail(characterId)
    }

    if (uiState.character != null) {
        Details(uiState.character!!, onBackClick = navigateToList)
    } else {
        EmptyScreenContent(Modifier.fillMaxSize())
    }
}

@Composable
private fun Details(
    character: Characters,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
                    }
                }
            )
        },
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars),
    ) { paddingValues ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )

            SelectionContainer {
                Column(Modifier.padding(12.dp)) {
                    Text(character.name, style = MaterialTheme.typography.headlineMedium)
                    Spacer(Modifier.height(6.dp))
                    Info(stringResource(R.string.label_location), character.location.name)
                    Info(stringResource(R.string.label_status), character.status)
                    Info(stringResource(R.string.label_species), character.species)
                    Info(stringResource(R.string.label_gender), character.gender)
                }
            }
        }
    }
}

@Composable
private fun Info(
    label: String,
    data: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier.padding(vertical = 4.dp)) {
        Spacer(Modifier.height(6.dp))
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("$label: ")
                }
                append(data)
            }
        )
    }
}

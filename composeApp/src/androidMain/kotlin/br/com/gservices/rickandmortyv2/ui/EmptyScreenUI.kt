package br.com.gservices.rickandmortyv2.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EmptyScreenContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(
                width = 15.dp,
                height = 15.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = Color.DarkGray,
            strokeWidth = 6.dp
        )
    }
}

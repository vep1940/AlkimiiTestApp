package com.vep1940.alkimiitestapp.presentation.screen.detail.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.vep1940.alkimiitestapp.presentation.screen.detail.model.DetailScreenState
import com.vep1940.alkimiitestapp.presentation.theme.AlkimiiTestAppTheme
import kotlinx.coroutines.delay

@Composable
internal fun DetailScreen(
    uiState: DetailScreenState,
) {
    AnimatedContent(targetState = uiState) { state ->
        when (state) {
            DetailScreenState.Loading -> DetailLoadingScreen()
            is DetailScreenState.Idle -> DetailIdleScreen(display = state.value)
            DetailScreenState.Error -> DetailErrorScreen()
        }
    }
}

@Preview
@Composable
private fun ListScreenPreview() {
    var uiState: DetailScreenState by remember { mutableStateOf(DetailScreenState.Loading) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            uiState = DetailScreenState.Idle(value = makeCharacterDisplay())
            delay(1000L)
            uiState = DetailScreenState.Error
            delay(1000L)
            uiState = DetailScreenState.Loading
        }
    }

    AlkimiiTestAppTheme {
        DetailScreen(uiState = uiState)
    }
}
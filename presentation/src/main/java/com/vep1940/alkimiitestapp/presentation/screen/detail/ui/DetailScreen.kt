package com.vep1940.alkimiitestapp.presentation.screen.detail.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.vep1940.alkimiitestapp.presentation.screen.detail.model.DataStatus
import com.vep1940.alkimiitestapp.presentation.screen.detail.model.DetailScreenAction
import com.vep1940.alkimiitestapp.presentation.screen.detail.model.DetailScreenState
import com.vep1940.alkimiitestapp.presentation.theme.AlkimiiTestAppTheme
import kotlinx.coroutines.delay

@Composable
internal fun DetailScreen(
    uiState: DetailScreenState,
    action: DetailScreenAction,
) {
    AnimatedContent(targetState = uiState.dataStatus) { dataStatus ->
        when (dataStatus) {
            DataStatus.LOADING -> DetailLoadingScreen()
            DataStatus.ERROR -> DetailErrorScreen(okErrorClick = action.onClickError)
            DataStatus.IDLE -> uiState.data?.let {
                DetailIdleScreen(display = uiState.data, favAction = action.onClickFavourite)
            }
        }
    }
}

@Preview
@Composable
private fun ListScreenPreview() {
    var uiState: DetailScreenState by remember { mutableStateOf(DetailScreenState.makeInitialState()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            uiState = DetailScreenState(
                data = makeCharacterDisplay(),
                dataStatus = DataStatus.IDLE
            )
            delay(1000L)
            uiState = DetailScreenState.makeInitialState().copy(dataStatus = DataStatus.ERROR)
            delay(1000L)
            uiState = DetailScreenState.makeInitialState()
        }
    }

    AlkimiiTestAppTheme {
        DetailScreen(
            uiState = uiState,
            action = DetailScreenAction(onClickError = {}, onClickFavourite = {})
        )
    }
}
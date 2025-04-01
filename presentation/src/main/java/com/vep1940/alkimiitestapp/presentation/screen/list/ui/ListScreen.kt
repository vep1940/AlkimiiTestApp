package com.vep1940.alkimiitestapp.presentation.screen.list.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.vep1940.alkimiitestapp.presentation.screen.list.model.ListScreenAction
import com.vep1940.alkimiitestapp.presentation.screen.list.model.ListScreenState
import com.vep1940.alkimiitestapp.presentation.theme.AlkimiiTestAppTheme
import kotlinx.coroutines.delay

@Composable
internal fun ListScreen(
    uiState: ListScreenState,
    action: ListScreenAction,
) {
    AnimatedContent(targetState = uiState) { state ->
        when(state) {
            ListScreenState.Loading -> ListLoadingScreen()
            is ListScreenState.Idle -> ListIdleScreen(display = state, action = action)
            ListScreenState.Error -> ListErrorScreen()
        }
    }
}

@Preview
@Composable
private fun ListScreenPreview() {
    var uiState: ListScreenState by remember { mutableStateOf(ListScreenState.Loading) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            uiState = ListScreenState.Idle(value = makeCharacterListItemsDisplay())
            delay(1000L)
            uiState = ListScreenState.Error
            delay(1000L)
            uiState = ListScreenState.Loading
        }
    }

    AlkimiiTestAppTheme {
        ListScreen(
            uiState = uiState,
            action = ListScreenAction {  }
        )
    }
}
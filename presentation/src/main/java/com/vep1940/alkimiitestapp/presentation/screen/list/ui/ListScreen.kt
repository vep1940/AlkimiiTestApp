package com.vep1940.alkimiitestapp.presentation.screen.list.ui

import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vep1940.alkimiitestapp.presentation.components.FadeAnimatedVisibility
import com.vep1940.alkimiitestapp.presentation.model.GenderDisplay
import com.vep1940.alkimiitestapp.presentation.model.getText
import com.vep1940.alkimiitestapp.presentation.screen.list.model.ListScreenAction
import com.vep1940.alkimiitestapp.presentation.screen.list.model.ListScreenState
import com.vep1940.alkimiitestapp.presentation.screen.list.model.PaginationState
import com.vep1940.alkimiitestapp.presentation.theme.AlkimiiTestAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun ListScreen(
    uiState: ListScreenState,
    action: ListScreenAction,
) {

    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
        ) {
            GenderDisplay.entries.forEach { genderDisplay ->
                GenderFilter(
                    genderDisplay = genderDisplay,
                    isSelected = uiState.filterSelected == genderDisplay,
                    onClick = {
                        action.onClickFilter(genderDisplay)
                        scope.launch {
                            lazyListState.scrollToItem(0)
                        }
                    }
                )
            }
        }

        val transition = updateTransition(targetState = uiState)

        transition.FadeAnimatedVisibility(visible = { it.showList }) {
            ListData(uiState = uiState, action = action, lazyListState = lazyListState)
        }

        transition.FadeAnimatedVisibility(visible = { it.showLoadingScreen }) {
            ListLoading()
        }

        transition.FadeAnimatedVisibility(visible = { it.showErrorScreen }) {
            ListError(action.onEnd)
        }
    }
}

@Composable
private fun GenderFilter(
    modifier: Modifier = Modifier,
    genderDisplay: GenderDisplay,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(percent = 25),
        colors = CardDefaults.cardColors(
            contentColor = Color.White,
            containerColor = if (isSelected) {
                Color.DarkGray
            } else {
                Color.Gray
            }
        ),
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(text = genderDisplay.getText(), modifier = Modifier.padding(8.dp))
    }
}

@Preview
@Composable
private fun ListScreenPreview() {
    var uiState: ListScreenState by remember { mutableStateOf(ListScreenState.makeInitialState()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            uiState = ListScreenState.makeInitialState().copy(
                data = makeCharacterListItemsDisplay()
            )
            delay(1000L)
            uiState = ListScreenState.makeInitialState().copy(
                paginationState = PaginationState.ERROR
            )
            delay(1000L)
            uiState = ListScreenState.makeInitialState()
        }
    }

    AlkimiiTestAppTheme {
        ListScreen(
            uiState = uiState,
            action = ListScreenAction(
                onClickItem = {},
                onClickFilter = {},
                onClickFav = {},
                onEnd = {},
            )
        )
    }
}
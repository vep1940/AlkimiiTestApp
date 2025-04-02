package com.vep1940.alkimiitestapp.presentation.screen.list

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vep1940.alkimiitestapp.presentation.screen.list.Constant.LIST_SCREEN_ROUTE
import com.vep1940.alkimiitestapp.presentation.screen.list.model.ListScreenAction
import com.vep1940.alkimiitestapp.presentation.screen.list.ui.ListScreen
import org.koin.androidx.compose.koinViewModel

object Constant {
    const val LIST_SCREEN_ROUTE = "list-screen"
}

fun NavGraphBuilder.listScreen(
    onClickItem: (id: Int) -> Unit,
) {
    composable(route = LIST_SCREEN_ROUTE) {
        val viewModel: ListViewModel = koinViewModel()
        val screenState by viewModel.screenState.collectAsStateWithLifecycle()
        val action = ListScreenAction(
            onClickItem = onClickItem,
            onClickFilter = { viewModel.applyGender(it) },
            onClickFav = { viewModel.onClickFav(it) },
            onEnd = { viewModel.getNextCharactersPage() },
        )
        ListScreen(uiState = screenState, action = action)
    }
}
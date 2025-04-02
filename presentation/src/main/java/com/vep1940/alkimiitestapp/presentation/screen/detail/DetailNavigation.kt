package com.vep1940.alkimiitestapp.presentation.screen.detail

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.vep1940.alkimiitestapp.presentation.screen.detail.Constant.DETAIL_SCREEN_ROUTE
import com.vep1940.alkimiitestapp.presentation.screen.detail.Constant.ID_PARAM
import com.vep1940.alkimiitestapp.presentation.screen.detail.Constant.URL_DEEPLINK
import com.vep1940.alkimiitestapp.presentation.screen.detail.model.DetailScreenAction
import com.vep1940.alkimiitestapp.presentation.screen.detail.ui.DetailScreen
import org.koin.androidx.compose.koinViewModel

private object Constant {
    const val DETAIL_SCREEN_ROUTE = "detail-screen"
    const val ID_PARAM = "detail-screen-id-param"
    const val URL_DEEPLINK = "alkimiirickandmorty://character"
}

fun NavGraphBuilder.detailScreen() {
    composable(
        route = "$DETAIL_SCREEN_ROUTE/{$ID_PARAM}",
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "$URL_DEEPLINK/{$ID_PARAM}"
                action = Intent.ACTION_VIEW
            }
        ),
    ) {
        val viewModel: DetailViewModel = koinViewModel()
        val screenState by viewModel.screenState.collectAsStateWithLifecycle()
        DetailScreen(
            uiState = screenState,
            action = DetailScreenAction(
                onClickFavourite = { viewModel.onClickFav() },
                onClickError = { viewModel.getCharacterData() }
            )
        )
    }
}

fun NavController.navigateToDetailScreen(id: Int) {
    navigate("$DETAIL_SCREEN_ROUTE/$id")
}

internal class DetailScreenArgs private constructor(val id: Int) {
    constructor(savedStateHandle: SavedStateHandle) :
            this((checkNotNull(savedStateHandle[ID_PARAM]) as String).toInt())
}
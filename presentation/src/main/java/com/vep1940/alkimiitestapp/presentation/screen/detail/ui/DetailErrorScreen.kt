package com.vep1940.alkimiitestapp.presentation.screen.detail.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vep1940.alkimiitestapp.presentation.components.ErrorDialog

@Composable
internal fun DetailErrorScreen(okErrorClick: () -> Unit) {
    ErrorDialog(onDismissRequest = okErrorClick)
}

@Preview
@Composable
private fun DetailErrorScreenPreview() {
    DetailErrorScreen(
        okErrorClick = { }
    )
}
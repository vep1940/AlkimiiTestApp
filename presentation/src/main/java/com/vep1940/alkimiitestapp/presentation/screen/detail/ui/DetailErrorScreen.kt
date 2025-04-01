package com.vep1940.alkimiitestapp.presentation.screen.detail.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vep1940.alkimiitestapp.presentation.components.ErrorDialog
import com.vep1940.alkimiitestapp.presentation.components.LoadingScreen

@Composable
internal fun DetailErrorScreen() {
    ErrorDialog()
}

@Preview
@Composable
private fun DetailErrorScreenPreview() {
    DetailErrorScreen()
}
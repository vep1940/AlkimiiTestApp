package com.vep1940.alkimiitestapp.presentation.screen.list.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vep1940.alkimiitestapp.presentation.components.ErrorDialog

@Composable
internal fun ListErrorScreen() {
    ErrorDialog()
}

@Preview
@Composable
private fun ListErrorScreenPreview() {
    ListErrorScreen()
}
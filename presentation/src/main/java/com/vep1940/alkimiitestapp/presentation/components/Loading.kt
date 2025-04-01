package com.vep1940.alkimiitestapp.presentation.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun Loading(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier)
}
package com.vep1940.alkimiitestapp.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.vep1940.alkimiitestapp.presentation.R

@Composable
internal fun String?.getOrUnknown() =
    this?.takeUnless { it.isBlank() } ?: stringResource(R.string.unknown_value)

@Composable
internal fun String?.getOrDefault(default: String) = this?.takeUnless { it.isBlank() } ?: default
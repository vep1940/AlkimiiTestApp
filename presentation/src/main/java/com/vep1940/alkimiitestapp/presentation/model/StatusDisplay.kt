package com.vep1940.alkimiitestapp.presentation.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.vep1940.alkimiitestapp.domain.model.Status
import com.vep1940.alkimiitestapp.presentation.R

enum class StatusDisplay {
    ALIVE,
    DEAD,
    UNKNOWN,
}

internal fun Status.toPresentation() = when(this) {
    Status.ALIVE -> StatusDisplay.ALIVE
    Status.DEAD -> StatusDisplay.DEAD
    Status.UNKNOWN -> StatusDisplay.UNKNOWN
}

@Composable
internal fun StatusDisplay.getText() = when(this){
    StatusDisplay.ALIVE -> stringResource(R.string.alive_status)
    StatusDisplay.DEAD -> stringResource(R.string.dead_status)
    StatusDisplay.UNKNOWN -> stringResource(R.string.unknown_status)
}
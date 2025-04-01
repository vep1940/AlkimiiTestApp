package com.vep1940.alkimiitestapp.presentation.components

import androidx.compose.foundation.basicMarquee
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
internal fun OneLineText(
    text: String,
    style: TextStyle = TextStyle.Default,
) {
    Text(
        text = text,
        maxLines = 1,
        style = style,
        modifier = Modifier.basicMarquee(),
    )
}
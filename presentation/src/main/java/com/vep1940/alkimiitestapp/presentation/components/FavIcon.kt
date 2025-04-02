package com.vep1940.alkimiitestapp.presentation.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.vep1940.alkimiitestapp.presentation.R
import com.vep1940.alkimiitestapp.presentation.theme.DarkBlue

@Composable
internal fun FavIcon(
    isFavourite: Boolean,
    size: Dp,
    onClick: () -> Unit,
) {
    Crossfade(targetState = isFavourite) { isFav ->
        if (isFav) {
            Icon(
                painter = painterResource(R.drawable.ic_fav),
                tint = DarkBlue,
                contentDescription = null,
                modifier = Modifier
                    .size(size)
                    .clickable(onClick = onClick)
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.ic_no_fav),
                tint = DarkBlue,
                contentDescription = null,
                modifier = Modifier
                    .size(size)
                    .clickable(onClick = onClick)
            )
        }
    }
}
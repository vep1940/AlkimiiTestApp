package com.vep1940.alkimiitestapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.vep1940.alkimiitestapp.presentation.R

@Composable
internal fun AsyncImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_async_image_placeholder),
        error = painterResource(R.drawable.ic_async_image_error),
        fallback = painterResource(R.drawable.ic_async_image_fallback),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}
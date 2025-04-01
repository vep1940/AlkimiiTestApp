package com.vep1940.alkimiitestapp.presentation.screen.detail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vep1940.alkimiitestapp.presentation.R
import com.vep1940.alkimiitestapp.presentation.components.AsyncImage
import com.vep1940.alkimiitestapp.presentation.components.OneLineText
import com.vep1940.alkimiitestapp.presentation.extensions.getOrDefault
import com.vep1940.alkimiitestapp.presentation.extensions.getOrUnknown
import com.vep1940.alkimiitestapp.presentation.model.getText
import com.vep1940.alkimiitestapp.presentation.screen.detail.model.CharacterDetailDisplay
import com.vep1940.alkimiitestapp.presentation.screen.detail.model.DetailScreenState

@Composable
internal fun DetailIdleScreen(
    display: CharacterDetailDisplay,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp)
    ) {
        AsyncImage(
            imageUrl = display.imageUrl,
            modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp))
        )
        Separator()
        OneLineText(
            text = stringResource(R.string.name_field) + " ${display.name}",
            style = TextStyle.Default.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
        )
        Separator()
        OneLineText(text = stringResource(R.string.status_field) + " ${display.status.getText()}")
        Separator()
        OneLineText(text = stringResource(R.string.species_field) + " ${display.species.getOrUnknown()}")
        Separator()
        OneLineText(text = stringResource(R.string.subspecies_field) + " ${display.subspecies.getOrDefault("-")}")
        Separator()
        OneLineText(text = stringResource(R.string.gender_field) + " ${display.gender.getText()}")
        Separator()
        OneLineText(text = stringResource(R.string.origin_field) + " ${display.origin.getOrUnknown()}")
        Separator()
        OneLineText(text = stringResource(R.string.location_field) + " ${display.location.getOrUnknown()}")
    }
}

@Composable
private fun Separator() {
    Spacer(Modifier.size(4.dp))
    HorizontalDivider(thickness = 2.dp)
    Spacer(Modifier.size(4.dp))
}

@Preview
@Composable
private fun DetailIdleScreenPreview() {
    DetailIdleScreen(display = DetailScreenState.Idle(value = makeCharacterDisplay()).value)
}
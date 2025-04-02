package com.vep1940.alkimiitestapp.presentation.screen.detail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.vep1940.alkimiitestapp.presentation.components.FavIcon
import com.vep1940.alkimiitestapp.presentation.components.OneLineText
import com.vep1940.alkimiitestapp.presentation.extensions.getOrDefault
import com.vep1940.alkimiitestapp.presentation.extensions.getOrUnknown
import com.vep1940.alkimiitestapp.presentation.model.getText
import com.vep1940.alkimiitestapp.presentation.screen.detail.model.CharacterDetailDisplay
import com.vep1940.alkimiitestapp.presentation.screen.detail.ui.Constant.IMAGE_SIZE

private object Constant {
    val IMAGE_SIZE = 80.dp
}

@Composable
internal fun DetailIdleScreen(
    display: CharacterDetailDisplay,
    favAction: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                imageUrl = display.imageUrl,
                modifier = Modifier
                    .size(IMAGE_SIZE)
                    .clip(RoundedCornerShape(8.dp))
            )
            FavIcon(
                isFavourite = display.isFavourite,
                size = IMAGE_SIZE,
                onClick = favAction
            )
        }
        Separator()
        OneLineText(
            text = stringResource(R.string.name_field) + " ${display.name}",
            style = TextStyle.Default.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
        )
        DataItem(text = stringResource(R.string.status_field) + " ${display.status.getText()}")
        DataItem(text = stringResource(R.string.species_field) + " ${display.species.getOrUnknown()}")
        DataItem(
            text = stringResource(R.string.subspecies_field) + " ${
                display.subspecies.getOrDefault(
                    "-"
                )
            }"
        )
        DataItem(text = stringResource(R.string.gender_field) + " ${display.gender.getText()}")
        DataItem(text = stringResource(R.string.origin_field) + " ${display.origin.getOrUnknown()}")
        DataItem(text = stringResource(R.string.location_field) + " ${display.location.getOrUnknown()}")

        Button(
            onClick = favAction,
            modifier = Modifier
                .padding(top = 8.dp)
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            val resourceId = if (display.isFavourite) {
                R.string.remove_from_favs
            } else {
                R.string.add_to_favs
            }
            Text(text = stringResource(resourceId))
        }
    }
}

@Composable
private fun DataItem(text: String) {
    Separator()
    OneLineText(text = text)
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
    DetailIdleScreen(
        display = makeCharacterDisplay(),
        favAction = { },
    )
}
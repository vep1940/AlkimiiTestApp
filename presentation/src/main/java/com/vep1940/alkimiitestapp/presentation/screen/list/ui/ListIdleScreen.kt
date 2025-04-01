package com.vep1940.alkimiitestapp.presentation.screen.list.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vep1940.alkimiitestapp.presentation.R
import com.vep1940.alkimiitestapp.presentation.components.AsyncImage
import com.vep1940.alkimiitestapp.presentation.components.OneLineText
import com.vep1940.alkimiitestapp.presentation.extensions.getOrUnknown
import com.vep1940.alkimiitestapp.presentation.model.getText
import com.vep1940.alkimiitestapp.presentation.screen.list.model.CharacterListItemDisplay
import com.vep1940.alkimiitestapp.presentation.screen.list.model.ListScreenAction
import com.vep1940.alkimiitestapp.presentation.screen.list.model.ListScreenState
import com.vep1940.alkimiitestapp.presentation.screen.list.ui.Constant.IMAGE_SIZE

private object Constant {
    val IMAGE_SIZE = 60.dp
}

@Composable
internal fun ListIdleScreen(
    display: ListScreenState.Idle,
    action: ListScreenAction,
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(top = 16.dp),
    ) {
        items(
            items = display.value,
            key = { item -> item.id },
        ) { item ->
            CharacterListItem(
                item = item,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { action.onClickItem(item.id) },
            )
        }
    }
}

@Composable
private fun CharacterListItem(
    item: CharacterListItemDisplay,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                imageUrl = item.imageUrl,
                modifier = Modifier
                    .size(IMAGE_SIZE)
                    .clip(CircleShape),
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                OneLineText(text = stringResource(R.string.name_field) + " ${item.name}")
                OneLineText(text = stringResource(R.string.origin_field) + " ${item.origin.getOrUnknown()}")
                OneLineText(text = stringResource(R.string.gender_field) + " ${item.gender.getText()}")
            }

            Crossfade(targetState = item.isFavourite) { isFavourite ->
                if (isFavourite) {
                    FavIcon(resId = R.drawable.ic_fav)
                } else {
                    FavIcon(resId = R.drawable.ic_no_fav)
                }
            }
        }
    }
}

@Composable
private fun FavIcon(@DrawableRes resId: Int) {
    Image(
        painter = painterResource(resId),
        contentDescription = null,
        modifier = Modifier.size(IMAGE_SIZE)
    )
}

@Preview
@Composable
private fun ListIdleScreenPreview() {
    ListIdleScreen(
        display = ListScreenState.Idle(value = makeCharacterListItemsDisplay()),
        action = ListScreenAction { }
    )
}

@Preview
@Composable
private fun CharacterListItemPreview() {
    CharacterListItem(
        item = makeCharacterListItemDisplay()
    )
}
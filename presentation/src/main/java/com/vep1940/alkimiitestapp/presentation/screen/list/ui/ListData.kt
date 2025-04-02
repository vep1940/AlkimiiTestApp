package com.vep1940.alkimiitestapp.presentation.screen.list.ui

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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vep1940.alkimiitestapp.presentation.R
import com.vep1940.alkimiitestapp.presentation.components.AsyncImage
import com.vep1940.alkimiitestapp.presentation.components.EndDetectingLazyColumn
import com.vep1940.alkimiitestapp.presentation.components.FavIcon
import com.vep1940.alkimiitestapp.presentation.components.OneLineText
import com.vep1940.alkimiitestapp.presentation.extensions.getOrUnknown
import com.vep1940.alkimiitestapp.presentation.model.getText
import com.vep1940.alkimiitestapp.presentation.screen.list.model.CharacterListItemDisplay
import com.vep1940.alkimiitestapp.presentation.screen.list.model.ListScreenAction
import com.vep1940.alkimiitestapp.presentation.screen.list.model.ListScreenState
import com.vep1940.alkimiitestapp.presentation.screen.list.ui.Constant.IMAGE_SIZE
import com.vep1940.alkimiitestapp.presentation.screen.list.ui.Constant.ITEM_CONTENT_TYPE
import com.vep1940.alkimiitestapp.presentation.screen.list.ui.Constant.LAST_ITEM_CONTENT_TYPE
import com.vep1940.alkimiitestapp.presentation.screen.list.ui.Constant.LAST_ITEM_ID

private object Constant {
    val IMAGE_SIZE = 60.dp
    const val LAST_ITEM_ID = "LAST_ITEM_ID"
    const val LAST_ITEM_CONTENT_TYPE = 0
    const val ITEM_CONTENT_TYPE = 1
}

@Composable
internal fun ListData(
    uiState: ListScreenState,
    action: ListScreenAction,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    EndDetectingLazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        onEnd = action.onEnd,
    ) {
        items(
            items = uiState.data,
            key = { item -> item.id },
            contentType = { ITEM_CONTENT_TYPE }
        ) { item ->
            CharacterListItem(
                item = item,
                onClickFav = action.onClickFav,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { action.onClickItem(item.id) },
            )
        }

        if (uiState.showErrorFooter || uiState.showEndFooter) {
            item(
                key = LAST_ITEM_ID,
                contentType = LAST_ITEM_CONTENT_TYPE,
            ) {
                when {
                    uiState.showErrorFooter -> {
                        NextPageErrorMessage(action.onEnd)
                    }

                    else -> {
                        EndOfList()
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterListItem(
    item: CharacterListItemDisplay,
    onClickFav: (CharacterListItemDisplay) -> Unit,
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

            FavIcon(
                isFavourite = item.isFavourite,
                size = IMAGE_SIZE,
                onClick = { onClickFav(item) }
            )
        }
    }
}

@Composable
private fun NextPageErrorMessage(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        OneLineText(text = stringResource(R.string.next_page_error_message))
        Button(onClick = onClick) {
            OneLineText(text = stringResource(R.string.next_page_error_button))
        }
    }
}

@Composable
private fun EndOfList() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        OneLineText(text = stringResource(R.string.list_end_reached))
    }
}

@Preview
@Composable
private fun ListIdleScreenPreview() {
    ListData(
        uiState = ListScreenState.makeInitialState().copy(
            data = makeCharacterListItemsDisplay()
        ),
        action = ListScreenAction(
            onClickItem = {},
            onClickFilter = {},
            onClickFav = {},
            onEnd = {},
        )
    )
}

@Preview
@Composable
private fun CharacterListItemPreview() {
    CharacterListItem(
        item = makeCharacterListItemDisplay(),
        onClickFav = {},
    )
}

@Preview
@Composable
private fun NextPageErrorMessagePreview() {
    NextPageErrorMessage { }
}

@Preview
@Composable
private fun EndOfListPreview() {
    EndOfList()
}
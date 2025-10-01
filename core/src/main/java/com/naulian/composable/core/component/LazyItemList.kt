package com.naulian.composable.core.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.naulian.composable.core.model.Item
import com.naulian.modify.SemiBold

@Composable
fun LazyItemList(items: List<Item>, onClickItem: (Item) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            HorizontalDivider()
        }

        items(items = items) {
            ItemUI(
                item = it,
                onClick = { onClickItem(it) }
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun ItemUI(item: Item, onClick: () -> Unit, modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clickable { onClick() }
    ) {
        val (texts, graphic) = createRefs()

        item.component(
            Modifier
                .size(120.dp)
                .constrainAs(graphic) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Column(
            modifier = Modifier
                .constrainAs(texts) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(graphic.start)

                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .padding(end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = item.primaryText,
                style = MaterialTheme.typography.titleMedium.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = item.secondaryText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
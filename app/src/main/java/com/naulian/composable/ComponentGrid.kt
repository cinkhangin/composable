package com.naulian.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.acc.accItemList
import com.naulian.composable.core.theme.ComposablePreview
import com.naulian.composable.icc.iccItemList
import com.naulian.composable.scc.sccItemList


@Preview(device = "id:Nexus 9")
@Composable
private fun ComponentGridPreview() {
    ComposablePreview(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(120.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Text("Static CC Previews")
            }

            items(items = sccItemList) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)) {
                    it.previewComponent(Modifier.fillMaxSize())
                }
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Text("Interactive CC Previews")
            }

            items(items = iccItemList) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)) {
                    it.previewComponent(Modifier.fillMaxSize())
                }
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Text("Animated CC Previews")
            }

            items(items = accItemList) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)) {
                    it.previewComponent(Modifier.fillMaxSize())
                }
            }
        }
    }
}
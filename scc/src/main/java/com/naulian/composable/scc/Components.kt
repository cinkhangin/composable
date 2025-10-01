package com.naulian.composable.scc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.theme.ComposablePreview
import com.naulian.composable.scc.cornered_box.CorneredBox
import com.naulian.composable.scc.grid_background.gridBackground
import com.naulian.composable.scc.neumorphic.neumorphicUp

@Composable
fun NeumorphismComponent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(10)
            ).padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .neumorphicUp(
                    shape = RoundedCornerShape(10),
                    shadowPadding = 6.dp
                )
        )
    }
}

@Preview
@Composable
private fun NeumorphismComponentPreview() {
    ComposablePreview {
        NeumorphismComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun GridBgComponent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .gridBackground(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                lineColor = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(10)
            )
    )
}

@Preview
@Composable
private fun GridBgComponentPreview() {
    ComposablePreview {
        GridBgComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun CorneredBoxComponent(modifier: Modifier = Modifier) {
    CorneredBox(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(10)
            )
    )
}

@Preview
@Composable
private fun CorneredBoxComponentPreview() {
    ComposablePreview {
        CorneredBoxComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}
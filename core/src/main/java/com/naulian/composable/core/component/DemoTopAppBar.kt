package com.naulian.composable.core.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.naulian.modify.HugeIcons
import com.naulian.modify.SemiBold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoTopAppBar(
    component: ComposableComponent,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    enableBack: Boolean = true
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            if (enableBack) {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(HugeIcons.Back),
                        contentDescription = "Back Icon"
                    )
                }
            }
        },
        title = {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = component.name,
                    style = MaterialTheme.typography.titleMedium.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Contributed by ${component.contributor}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}
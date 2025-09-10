package com.naulian.composable.screens.calenderTopBar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.component.CodeBlock
import com.naulian.composable.theme.ComposableTheme
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.columnItem


@OptIn(ExperimentalModifyApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CalenderTopBarUi(onBack: () -> Unit = {}) {

    BackHandler {
        onBack()
    }

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        topBar = {
            CalenderTopBar(
                onDateSelected = { selectedDate ->
                    // do opp like filtering etc
                }
            )
        },
    ) { scaffoldPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(20.dp)
        ) {
            columnItem(
                verticalArrangement = Arrangement.spacedBy(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CodeBlock(
                    source = calenderTopBarCode,
                    language = "kotlin",
                )
            }
        }
    }
}


@Preview
@Composable
private fun BottomBarUiPreview() {
    ComposableTheme {
        CalenderTopBar()
    }
}
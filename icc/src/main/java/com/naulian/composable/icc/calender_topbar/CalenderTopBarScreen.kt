package com.naulian.composable.icc.calender_topbar

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
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.columnItem

@Composable
fun CalenderTopBarScreen() {
    val navController = LocalNavController.current

    CalenderTopBarScreenUI(
        onBack = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalModifyApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CalenderTopBarScreenUI(onBack: () -> Unit = {}) {
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        topBar = {
            CalenderTopBar(
                onDateSelected = { selectedDate ->
                    // do opp like filtering etc
                },
                onBack = onBack
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
        CalenderTopBar(onBack = {})
    }
}
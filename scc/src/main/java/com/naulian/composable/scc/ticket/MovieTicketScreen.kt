package com.naulian.composable.scc.ticket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar

@Composable
fun MovieTicketScreen() {
    val navController = LocalNavController.current

    MovieTicketScreenUI(
        onBack = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTicketScreenUI(onBack: () -> Unit = {}) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Movie Ticket",
                onBack = onBack
            )
        }
    ) { scaffoldPadding ->

        val verticalShapeTicketSource = remember { verticalTicketShapeCode }
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            MovieTicket()

            CodeBlock(
                source = verticalShapeTicketSource,
                language = "kotlin"
            )
        }
    }
}
package com.naulian.composable.icc.cylindricalButton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar

@Composable
fun CylindricalButtonsScreen() {
    val navController = LocalNavController.current

    CylindricalButtonsScreenUI(onBack = { navController.navigateUp() })
}

@Composable
fun CylindricalButtonsScreenUI(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Depth Card",
                onBack = onBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Large emergency-style button
                IndustrialCylinderButton(
                    text = "Long Press",
                    buttonSize = 140,
                    cylinderDepth = 25
                )

                // Medium button
                IndustrialCylinderButton(
                    text = "Long Press",
                    buttonSize = 100,
                    cylinderDepth = 18
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Large emergency-style button
                IndustrialSquareButton(
                    text = "Long Press",
                    buttonSize = 140,
                    cylinderDepth = 25
                )

                // Medium button
                IndustrialSquareButton(
                    text = "Long Press",
                    buttonSize = 100,
                    cylinderDepth = 18
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Large emergency-style button
                IndustrialCylinderButtonOneState(
                    text = "Click",
                    buttonSize = 140,
                    cylinderDepth = 25
                )
            }
            CodeBlock(source = cylindricalButtonCode, language = "Kotlin")
        }
    }
}
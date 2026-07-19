package com.naulian.composable.component.icc

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A visually enhanced, clickable progress stepper.
 * Features animations, elevation, and a modern design.
 *
 * @param totalSteps total number of steps
 * @param currentStep currently active step (1-based index)
 * @param modifier Modifier for custom styling
 * @param activeColor color for completed/active steps
 * @param inactiveColor color for upcoming steps
 * @param onStepClick callback when a step is clicked
 * @param stepLabels optional labels for each step
 */
@Composable
fun ProgressTick(
    totalSteps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF4CAF50),
    inactiveColor: Color = Color(0xFFE0E0E0),
    onStepClick: (Int) -> Unit,
    stepLabels: List<String> = emptyList()
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress circles and connecting lines
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(totalSteps) { index ->
                val step = index + 1
                val isActive = step <= currentStep
                val isCurrent = step == currentStep

                // Animated properties
                val color by animateColorAsState(
                    targetValue = if (isActive) activeColor else inactiveColor,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )

                val scale by animateFloatAsState(
                    targetValue = if (isCurrent) 1.1f else 1.0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )

                val elevation by animateDpAsState(
                    targetValue = if (isCurrent) 8.dp else if (isActive) 4.dp else 0.dp,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )

                // Step circle
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .scale(scale)
                        .clip(CircleShape)
                        .shadow(
                            elevation = elevation,
                            shape = CircleShape,
                            ambientColor = if (isActive) activeColor.copy(alpha = 0.3f) else Color.Transparent,
                            spotColor = if (isActive) activeColor.copy(alpha = 0.3f) else Color.Transparent
                        )
                        .background(
                            color = color,
                            shape = CircleShape
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onStepClick(step) }
                        )
                ) {
                    val textColor by animateColorAsState(
                        targetValue = if (isActive) Color.White else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        animationSpec = tween(durationMillis = 300)
                    )

                    Text(
                        text = if (isActive) "✓" else step.toString(),
                        color = textColor,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }

                // Connecting line (except for the last step)
                if (step != totalSteps) {
                    val lineColor by animateColorAsState(
                        targetValue = if (step < currentStep) activeColor else inactiveColor,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )

                    Box(
                        modifier = Modifier
                            .height(3.dp)
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(lineColor)
                    )
                }
            }
        }

        // Step labels (if provided)
        if (stepLabels.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(totalSteps) { index ->
                    val step = index + 1
                    val isActive = step <= currentStep
                    val label = stepLabels.getOrNull(index) ?: "Step $step"

                    val labelColor by animateColorAsState(
                        targetValue = if (isActive) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        },
                        animationSpec = tween(durationMillis = 300)
                    )

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            color = labelColor,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = if (isActive) FontWeight.Medium else FontWeight.Normal,
                                fontSize = 11.sp
                            ),
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ColumnScope.StepProgressImpl(modifier: Modifier = Modifier) {
    var currentStep by remember { mutableIntStateOf(1) }
    val totalSteps = 5

    // Professional step content with better descriptions
    val stepContent = remember {
        listOf(
            "Welcome to your journey! Let's get started with the onboarding process.",
            "Perfect! Now let's set up your preferences and configure the basic settings.",
            "Great progress! Time to review your configuration and make any adjustments.",
            "Almost there! Please review all your settings before we finalize everything.",
            "Congratulations! You have successfully completed the entire setup process."
        )
    }

    val stepTitles = remember {
        listOf(
            "Getting Started",
            "Configuration",
            "Customization",
            "Final Review",
            "All Done!"
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF4CAF50).copy(alpha = 0.1f),
                        Color(0xFF2196F3).copy(alpha = 0.1f)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Setup Progress",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Step $currentStep of $totalSteps",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(32.dp))

    // Progress bar with ticks
    ProgressTick(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepLabels = listOf("Start", "Setup", "Configure", "Review", "Complete"),
        onStepClick = { step ->
            currentStep = step
        }
    )

    Spacer(modifier = Modifier.height(40.dp))

    // Professional content card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color(0xFF4CAF50).copy(alpha = 0.1f),
                spotColor = Color(0xFF4CAF50).copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Crossfade(
            targetState = currentStep,
            animationSpec = tween(500),
            label = "step_transition"
        ) { targetStep ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Step title
                Text(
                    text = stepTitles.getOrElse(targetStep - 1) { "Step $targetStep" },
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Step content
                Text(
                    text = stepContent.getOrElse(targetStep - 1) { "" },
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 24.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.weight(1f))

    // Professional gradient button
    Button(
        onClick = {
            if (currentStep < totalSteps) {
                currentStep++
            } else {
                currentStep = 1
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0xFF4CAF50).copy(alpha = 0.3f),
                spotColor = Color(0xFF4CAF50).copy(alpha = 0.3f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4CAF50)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (currentStep < totalSteps) "Continue to Next Step" else "Start Over",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                color = Color.White
            )

            if (currentStep < totalSteps) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "→",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }
        }
    }

    // Helper text
    Text(
        text = if (currentStep < totalSteps) {
            "Tap any step above to jump directly to it"
        } else {
            "You've completed all steps! 🎉"
        },
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 16.dp)
    )
}

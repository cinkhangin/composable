package com.naulian.composable.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.naulian.anhance.copyString
import com.naulian.glow.Code
import com.naulian.glow.theme.GlowTheme
import com.naulian.glow.theme.Theme
import com.naulian.modify.HugeIcons
import com.naulian.neumorphic.ComposableTheme

@Composable
fun CodeBlock(
    modifier: Modifier = Modifier,
    source: String,
    language: String = "txt",
    codeName: String = "",
    codeTheme: Theme = if (isSystemInDarkTheme()) GlowTheme.defaultDark else GlowTheme.defaultLight,
) {
    val context = LocalContext.current

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (name, action, code) = createRefs()

            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary.copy(0.2f))
                    .constrainAs(name) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = codeName.ifEmpty { language },
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }

            IconButton(
                modifier = Modifier.constrainAs(action) {
                    end.linkTo(parent.end)
                    top.linkTo(name.top)
                    bottom.linkTo(name.bottom)
                },
                onClick = { context.copyString(source) }
            ) {
                Icon(painter = painterResource(id = HugeIcons.Copy), contentDescription = null)
            }

            SelectionContainer(
                modifier = Modifier
                    .constrainAs(code) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(name.bottom)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    },
            ) {
                Code(
                    source = source,
                    language = language,
                    theme = codeTheme
                )
            }
        }
    }
}

@Preview
@Composable
private fun CodeBlockPreview() {
    ComposableTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
        ) {
            CodeBlock(
                source = """
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .constrainAs(name) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = codeName.ifEmpty { language },
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                """.trimIndent(),
                language = "kt",
                codeName = "Preview"
            )
        }
    }
}
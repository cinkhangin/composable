package com.naulian.composable.acc.typing

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.modify.columnItem
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay

@Composable
fun TypingTextScreen() {
    val navController = LocalNavController.current

    TypingTextScreenUI(
        onBack = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TypingTextScreenUI(onBack: () -> Unit = {}) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Animated Components",
                onBack = onBack
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(20.dp)
        ) {
            columnItem {
                TypingText(text = "This is a typing text animation")
                Spacer(modifier = Modifier.height(20.dp))

                CodeBlock(
                    source = """
                        @Composable
                        fun TypingText(
                            text: String,
                            modifier: Modifier = Modifier,
                            color: Color = Color.Unspecified,
                            fontSize: TextUnit = TextUnit.Unspecified,
                            fontStyle: FontStyle? = null,
                            fontWeight: FontWeight? = null,
                            fontFamily: FontFamily? = null,
                            letterSpacing: TextUnit = TextUnit.Unspecified,
                            textDecoration: TextDecoration? = null,
                            textAlign: TextAlign? = null,
                            lineHeight: TextUnit = TextUnit.Unspecified,
                            overflow: TextOverflow = TextOverflow.Clip,
                            softWrap: Boolean = true,
                            maxLines: Int = Int.MAX_VALUE,
                            minLines: Int = 1,
                            onTextLayout: ((TextLayoutResult) -> Unit)? = null,
                            style: TextStyle = LocalTextStyle.current
                        ) {
                            var textIndex by remember { mutableIntStateOf(0) }
                            LaunchedEffect(Unit) {
                                while (true) {
                                    textIndex++
                                    delay(80)
                                }
                            }
                            
                            Text(
                                modifier = modifier,,
                                text = text.take(textIndex % text.length),
                                fontStyle = fontStyle,
                                fontSize = fontSize,
                                fontWeight = fontWeight,
                                color = color,
                                fontFamily = fontFamily,
                                letterSpacing = letterSpacing,
                                textDecoration = textDecoration,
                                textAlign = textAlign,
                                lineHeight = lineHeight,
                                overflow = overflow,
                                softWrap = softWrap,
                                maxLines = maxLines,
                                minLines = minLines,
                                onTextLayout = onTextLayout,
                                style = style
                            )
                        }
                    """.trimIndent(),
                    codeName = "TypingText",
                    language = "kotlin"
                )
            }
        }
    }
}

@Composable
fun TypingText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    delay: Long = 80,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current
) {
    var textIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            textIndex++
            delay(delay)
        }
    }

    Text(
        modifier = modifier,
        text = text.take(textIndex % (text.length + 1)),
        fontStyle = fontStyle,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style
    )
}
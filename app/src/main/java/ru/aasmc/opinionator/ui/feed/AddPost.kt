package ru.aasmc.opinionator.ui.feed

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.aasmc.opinionator.ui.theme.darkTransparent

@ExperimentalAnimationApi
@Composable
fun AddPost(
    show: Boolean,
    onDoneClicked: (String) -> Unit
) {

    Box(
        contentAlignment = Alignment.Center
    ) {

        AnimatedVisibility(
            visible = show,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(darkTransparent)
                    .padding(horizontal = 16.dp)
            )
        }

        AnimatedVisibility(
            visible = show,
            enter =  slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = fadeOut()
        ) {

            var postText by remember {
                mutableStateOf("")
            }
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Add your opinion here",
                    style = TextStyle(MaterialTheme.colors.secondary, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Card(
                    modifier = Modifier.animateContentSize()
                ) {
                    TextField(
                        value = postText,
                        textStyle = TextStyle(color = MaterialTheme.colors.primary),
                        onValueChange = { value ->
                            postText = value
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            onDoneClicked(postText)
                        }),
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 50.dp)
                    )
                }
            }
        }
    }
}
















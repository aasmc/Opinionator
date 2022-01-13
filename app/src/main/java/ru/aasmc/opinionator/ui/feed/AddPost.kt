package ru.aasmc.opinionator.ui.feed

import androidx.compose.animation.animateContentSize
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

@Composable
fun AddPost(
    onDoneClicked: (String) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(darkTransparent)
            .padding(horizontal = 16.dp)
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
                    textStyle = TextStyle(color =  MaterialTheme.colors.primary),
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
















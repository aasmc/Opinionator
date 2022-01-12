package ru.aasmc.opinionator.ui.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.aasmc.opinionator.R
import ru.aasmc.opinionator.models.Post

@Composable
fun CommentBar(
    post: Post
) {
    val likeImage = if (post.hasBeenLiked) R.drawable.favorite else R.drawable.favorite_border
    val viewModel: FeedViewModel = viewModel()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Image(
            painter = painterResource(id = likeImage),
            contentDescription = "Favourite",
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier
                .size(16.dp)
                .clickable {
                    viewModel.postLiked(post)
                }
        )
        Text(
            text = "${post.likes}",
            modifier = Modifier.padding(start = 4.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.comment),
            contentDescription = "Comment",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(16.dp),
            colorFilter = ColorFilter.tint(Color.Black)
        )
        Text(
            text = "${post.comments}",
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

















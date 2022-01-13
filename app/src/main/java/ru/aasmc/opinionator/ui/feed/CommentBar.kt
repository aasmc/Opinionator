package ru.aasmc.opinionator.ui.feed

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.aasmc.opinionator.R
import ru.aasmc.opinionator.models.Post
import ru.aasmc.opinionator.models.User
import java.util.*

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
        LikeCount(post = post)
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

private enum class LikeAnimationState {
    Started,
    Finished
}

@Composable
private fun LikeCount(post: Post) {
    val previousLikeCount = remember {
        mutableStateOf(post.likes)
    }

    val likeCountAnimation = useLikeCountAnimation(likes = post.likes)
    if (likeCountAnimation.finished) {
        previousLikeCount.value = post.likes
    }

    Box(modifier = Modifier.padding(start = 4.dp)) {
        Text(text = "${post.likes}")
        Text(
            text = "${previousLikeCount.value}",
            modifier = Modifier
                .graphicsLayer(
                    translationY = likeCountAnimation.translation,
                    alpha = likeCountAnimation.alpha
                )
        )
    }
}

data class LikeCountAnimation(
    val alpha: Float,
    val translation: Float,
    val finished: Boolean
)

@Composable
private fun useLikeCountAnimation(likes: Int): LikeCountAnimation {
    val state = remember(likes) {
        MutableTransitionState(LikeAnimationState.Started)
    }
    state.targetState = LikeAnimationState.Finished
    val transition = updateTransition(state, "Like Count Transition")

    val translation by transition.animateDp(
        label = "Translation",
        transitionSpec = {
            spring(stiffness = Spring.StiffnessLow)
        }
    ) { animationState ->
        when (animationState) {
            LikeAnimationState.Started -> 0.dp
            LikeAnimationState.Finished -> (-16).dp
        }
    }

    val translationPx = with(LocalDensity.current) {
        translation.toPx()
    }

    val alpha by transition.animateFloat(
        label = "Alpha",
        transitionSpec = {
            spring(stiffness = Spring.StiffnessLow)
        }
    ) { animationState ->
        when (animationState) {
            LikeAnimationState.Started -> 1f
            LikeAnimationState.Finished -> 0f
        }
    }
    val isFinished = transition.currentState == transition.targetState
    return LikeCountAnimation(
        alpha = alpha,
        translation = translationPx,
        finished = isFinished
    )
}

@Preview
@Composable
private fun LikeCountPreview() {
    val post = Post(
        UUID.randomUUID(),
        "Test",
        User(R.drawable.default_account, "Test"),
        100, 100, false
    )
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        LikeCount(post = post)
    }
}













package ru.aasmc.opinionator.ui.feed

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.aasmc.opinionator.R
import ru.aasmc.opinionator.data.PostLoadingState
import ru.aasmc.opinionator.models.Post
import ru.aasmc.opinionator.models.User

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun FeedScreen() {
    Feed()
}

enum class FeedScreenShowing {
    Loading,
    Feed
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
private fun Feed() {
    val viewModel = viewModel<FeedViewModel>()
    val postLoadingState by viewModel.posts.collectAsState(initial = PostLoadingState.Loading)
    val currentScreen = when (postLoadingState) {
        PostLoadingState.Loading -> FeedScreenShowing.Loading
        is PostLoadingState.Populated -> FeedScreenShowing.Feed
    }
    Crossfade(
        targetState = currentScreen,
        animationSpec = tween(
            durationMillis = 800
        )
    ) { state ->
        when (state) {
            FeedScreenShowing.Loading -> LoadingFeed()
            FeedScreenShowing.Feed -> PopulatedFeed(
                posts = (postLoadingState as PostLoadingState.Populated).posts
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
@ExperimentalPagerApi
private fun PopulatedFeed(
    posts: List<Post>
) {
    var isShowingPostInput by remember {
        mutableStateOf(false)
    }
    val viewModel: FeedViewModel = viewModel()
    val rotationAnimation = animateFloatAsState(
        targetValue = if (isShowingPostInput) 405f else 0f
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isShowingPostInput = !isShowingPostInput }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "Add post",
                    modifier = Modifier.graphicsLayer(rotationZ = rotationAnimation.value)
                )
            }
        }
    ) {
        Box {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                item {
                    TopAppBar(
                        title = { Text(text = "Opinionator") },
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp
                    )
                }
                for (post in posts) {
                    item { Post(post = post) }
                }
            }
            AddPost(
                show = isShowingPostInput,
                onDoneClicked = {
                    isShowingPostInput = false
                    viewModel.addPost(it)
                })

        }
    }
}

@Composable
private fun LoadingFeed() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@ExperimentalPagerApi
private fun Post(post: Post) {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        ProfileItem(user = post.user, modifier = Modifier.padding(bottom = 16.dp))
        PostBody(post = post)
    }
}

@Composable
@ExperimentalPagerApi
private fun PostBody(
    post: Post
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = post.text)
                ImagePager(images = post.attachedImages)
                CommentBar(post = post)
            }
        }
    }
}

@Composable
private fun ProfileItem(
    modifier: Modifier = Modifier,
    user: User
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = user.profileImage),
            contentDescription = "Profile image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(48.dp)
                .shadow(8.dp, CircleShape)
        )
        Text(
            text = user.userName,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
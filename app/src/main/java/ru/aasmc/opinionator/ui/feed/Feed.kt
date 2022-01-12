package ru.aasmc.opinionator.ui.feed

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.aasmc.opinionator.R
import ru.aasmc.opinionator.data.PostLoadingState
import ru.aasmc.opinionator.models.Post
import ru.aasmc.opinionator.models.User

@ExperimentalPagerApi
@Composable
fun FeedScreen() {
    Feed()
}

@ExperimentalPagerApi
@Composable
private fun Feed() {
    val viewModel = viewModel<FeedViewModel>()
    val postLoadingState by viewModel.posts.collectAsState(initial = PostLoadingState.Loading)
    when (postLoadingState) {
        PostLoadingState.Loading -> LoadingFeed()
        is PostLoadingState.Populated -> PopulatedFeed(
            posts = (postLoadingState as PostLoadingState.Populated).posts)
    }
}

@Composable
@ExperimentalPagerApi
private fun PopulatedFeed(
    posts: List<Post>
) {
    var isShowingPostInput by remember {
        mutableStateOf(false)
    }
    val addPostIcon = if (isShowingPostInput) R.drawable.close else R.drawable.add
    val viewModel: FeedViewModel = viewModel()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isShowingPostInput = !isShowingPostInput }
            ) {
                Icon(
                    painter = painterResource(id = addPostIcon),
                    contentDescription = "Add post"
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
            if (isShowingPostInput) {
                AddPost(onDoneClicked = {
                    isShowingPostInput = false
                    viewModel.addPost(it)
                })
            }
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
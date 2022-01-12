package ru.aasmc.opinionator.ui.feed

import androidx.lifecycle.ViewModel
import ru.aasmc.opinionator.R
import ru.aasmc.opinionator.data.CreatePostUseCase
import ru.aasmc.opinionator.data.GetPostsUseCase
import ru.aasmc.opinionator.data.UpdatePostsUseCase
import ru.aasmc.opinionator.models.Post
import ru.aasmc.opinionator.models.User
import java.util.*

class FeedViewModel : ViewModel() {
    private val getPostsUseCase = GetPostsUseCase()
    private val updatePostsUseCase = UpdatePostsUseCase()
    private val createPostsUseCase = CreatePostUseCase()

    val posts = getPostsUseCase.getPosts()

    fun postLiked(post: Post) {
        val postIsLiked = !post.hasBeenLiked
        val newLikeCount = if (postIsLiked) post.likes + 1 else post.likes - 1
        val newPost = post.copy(hasBeenLiked = postIsLiked, likes = newLikeCount)
        updatePostsUseCase.updatePost(newPost)
    }

    fun addPost(postText: String) {
        val post = Post(
            UUID.randomUUID(),
            postText,
            User(R.drawable.default_account, "You"),
            0, 0, false
        )
        createPostsUseCase.createPost(post = post)
    }
}
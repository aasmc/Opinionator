package ru.aasmc.opinionator.data

import ru.aasmc.opinionator.models.Post

class CreatePostUseCase {
    fun createPost(post: Post) {
        PostData.posts = listOf(post) + PostData.posts
    }
}
package ru.aasmc.opinionator.data

import ru.aasmc.opinionator.models.Post

class UpdatePostsUseCase {
    fun updatePost(newPost: Post) {
        PostData.posts = PostData.posts.map { post ->
            if (post.id == newPost.id) {
                newPost
            } else {
                post
            }
        }
    }
}
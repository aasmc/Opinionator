package ru.aasmc.opinionator.data

import ru.aasmc.opinionator.models.Post

sealed class PostLoadingState {
    object Loading: PostLoadingState()
    data class Populated(val posts: List<Post>): PostLoadingState()
}

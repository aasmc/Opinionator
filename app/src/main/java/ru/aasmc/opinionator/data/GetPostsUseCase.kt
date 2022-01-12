package ru.aasmc.opinionator.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class GetPostsUseCase {
    fun getPosts(): Flow<PostLoadingState> {
        return PostData.postsFlow
            .map {
                PostLoadingState.Populated(it) as PostLoadingState
            }
            .onStart {
                emit(PostLoadingState.Loading)
                delay(2500)
            }
    }
}
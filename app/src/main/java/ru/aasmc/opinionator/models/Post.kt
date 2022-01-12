package ru.aasmc.opinionator.models

import java.util.*

data class Post(
    val id: UUID,
    val text: String,
    val user: User,
    val likes: Int,
    val comments: Int,
    val hasBeenLiked: Boolean,
    val attachedImages: List<Int> = emptyList()
)

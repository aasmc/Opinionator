package ru.aasmc.opinionator.models

import androidx.annotation.DrawableRes

data class User(
    @DrawableRes val profileImage: Int,
    val userName: String
)

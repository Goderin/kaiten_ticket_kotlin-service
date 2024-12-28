package ru.kaiten.entity

data class ErrorResponseEntity(
    val message: String,
    val error: String,
    val status: Int
)
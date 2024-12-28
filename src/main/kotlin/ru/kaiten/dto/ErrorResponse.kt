package ru.kaiten.dto

data class ErrorResponse(
    val message: String,
    val error: String,
    val status: Int
)
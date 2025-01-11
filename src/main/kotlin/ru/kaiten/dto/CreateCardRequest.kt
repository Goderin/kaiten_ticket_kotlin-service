package ru.kaiten.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateCardRequest(
    @JsonProperty("board_id")
    val boardId: Int,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("deadline")
    val deadline: String,
    @JsonProperty("type")
    val type: String
)

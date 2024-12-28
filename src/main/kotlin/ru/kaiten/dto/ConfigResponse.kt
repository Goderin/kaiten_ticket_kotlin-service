package ru.kaiten.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ConfigResponse(
    @JsonProperty("kaiten_urls")
    val kaitenUrls: List<KaitenUrl>,
)

data class KaitenUrl(
    @JsonProperty("url")
    val url: String,
    @JsonProperty("token")
    val token: String,
    @JsonProperty("primary")
    val primary: Boolean,
    @JsonProperty("board_id")
    val boardId: Int
)
package ru.kaiten.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateCardResponse (
    @JsonProperty("ticket_url")
    val ticketUrl: String
)
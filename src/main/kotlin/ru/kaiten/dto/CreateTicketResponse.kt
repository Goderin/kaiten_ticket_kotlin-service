package ru.kaiten.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateTicketResponse (
    @JsonProperty("ticket_url")
    val ticketUrl: String
)
package ru.kaiten.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateTicketResponse (
    @JsonProperty("ticket_url")
    val ticketUrl: String
)
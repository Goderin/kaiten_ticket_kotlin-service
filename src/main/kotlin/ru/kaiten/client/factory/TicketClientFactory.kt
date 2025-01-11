package ru.kaiten.client.factory

import ru.kaiten.client.CardClient
import ru.kaiten.exception.ClientNotFoundException

class TicketClientFactory(private val clients: Map<String, CardClient>) {
    fun getClient(systemType: String): CardClient {
        return clients[systemType] ?: throw ClientNotFoundException("Client not found for system: $systemType")
    }
}
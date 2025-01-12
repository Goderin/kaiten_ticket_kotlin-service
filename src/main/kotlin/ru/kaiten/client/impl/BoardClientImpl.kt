package ru.kaiten.client.impl

import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.kaiten.client.BoardClient
import ru.kaiten.exception.CreateEntryException

class BoardClientImpl(private val webClient: WebClient) : BoardClient {
    override fun createBoard(url: String, token: String, title: String, spaceId: Int): Int {
        val response = webClient.post()
            .uri("$url/api/board")
            .headers { headers ->
                headers.set("Authorization", "API-Key $token")
            }
            .bodyValue(
                mapOf(
                    "space_id" to spaceId,
                    "title" to title
                )
            )
            .retrieve()
            .onStatus(HttpStatus::isError) { clientResponse ->
                clientResponse.bodyToMono(String::class.java).flatMap { _ ->
                    Mono.error(CreateEntryException("Create board error"))
                }
            }
            .bodyToMono(Map::class.java)
            .block()

        return response["id"]?.toString()?.toIntOrNull()
            ?: throw CreateEntryException("Id board not found")
    }
}
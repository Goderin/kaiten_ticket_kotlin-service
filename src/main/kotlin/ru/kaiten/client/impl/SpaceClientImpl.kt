package ru.kaiten.client.impl

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.kaiten.client.SpaceClient
import ru.kaiten.exception.CreateEntryException

class SpaceClientImpl(private val webClient: WebClient) : SpaceClient {
    override fun createSpace(url: String, token: String, title: String): Int {
        val response = webClient.post()
            .uri("$url/api/space")
            .headers { headers ->
                headers.set("Authorization", "API-Key $token")
            }
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(mapOf("title" to title))
            .retrieve()
            .onStatus(HttpStatus::isError) { clientResponse ->
                clientResponse.bodyToMono(String::class.java).flatMap { body ->
                    Mono.error(CreateEntryException("Create space error"))
                }
            }
            .bodyToMono(Map::class.java)
            .block()

        return response["id"]?.toString()?.toIntOrNull()
            ?: throw CreateEntryException("Invalid or missing ID in response: $response")
    }
}
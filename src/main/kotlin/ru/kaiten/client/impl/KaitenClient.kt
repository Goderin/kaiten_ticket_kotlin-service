package ru.kaiten.client.impl

import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.kaiten.client.CardClient
import ru.kaiten.dto.CreateCardRequest
import ru.kaiten.exception.CreateEntryException

class KaitenClient(private val webClient: WebClient): CardClient {
    override fun createCard(url: String, token: String, bodyRequest: CreateCardRequest): Int {
        val response = webClient.post()
            .uri("$url/api/card")
            .headers { headers ->
                headers.set("Authorization", "API-Key $token")
            }
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(bodyRequest)
            .retrieve()
            .onStatus(HttpStatus::isError) { clientResponse ->
                clientResponse.bodyToMono(String::class.java).flatMap { _ ->
                    Mono.error(CreateEntryException("Create card error"))
                }
            }
            .bodyToMono(Map::class.java)
            .block()

        return response["id"]?.toString()?.toIntOrNull()
            ?: throw CreateEntryException("Id card not found")
    }

    override fun attachFiles(url: String, token: String, cardId: Int, files: List<MultipartFile>) {
        val body: MultiValueMap<String, Any> = LinkedMultiValueMap()

        files.forEachIndexed { index, file ->
            body.add(
                "file",
                object : ByteArrayResource(file.bytes) {
                    override fun getFilename(): String = file.originalFilename ?: "file$index"
                }
            )
        }

        webClient.post()
            .uri("$url/api/card/$cardId/attachment")
            .headers { headers ->
                headers.set("Authorization", "API-Key $token")
            }
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .bodyValue(body)
            .retrieve()
            .onStatus(HttpStatus::isError) { clientResponse ->
                clientResponse.bodyToMono(String::class.java).flatMap { _ ->
                    Mono.error(CreateEntryException("Create space error"))
                }
            }
    }

    override fun createChildCard(url: String, token: String, parentCardId: Int) {
        webClient.post()
            .uri("$url/api/card/$parentCardId/children")
            .headers { headers ->
                headers.set("Authorization", "API-Key $token")
            }
            .retrieve()
            .onStatus(HttpStatus::isError) { clientResponse ->
                clientResponse.bodyToMono(String::class.java).flatMap { _ ->
                    Mono.error(CreateEntryException("Create child card error"))
                }
            }
    }
}
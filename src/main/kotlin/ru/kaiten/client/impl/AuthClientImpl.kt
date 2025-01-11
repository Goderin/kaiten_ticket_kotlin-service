package ru.kaiten.client.impl

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.kaiten.client.AuthClient
import ru.kaiten.exception.AuthException

class AuthClientImpl(
    private val webClient: WebClient,
): AuthClient {
    override fun authenticate(url: String, formData: MultiValueMap<String, String>): String {
        val response = webClient.post()
            .uri(url)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .onStatus(HttpStatus::isError) { clientResponse ->
                clientResponse.bodyToMono(String::class.java).flatMap { _ ->
                    Mono.error(AuthException("Auth Error"))
                }
            }
            .bodyToMono(Map::class.java)
            .block()

        return response["access_token"]?.toString()
            ?: throw AuthException("Access token not found in response")
    }
}
package ru.kaiten.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import ru.kaiten.entity.ConfigResponse
import ru.kaiten.entity.KaitenUrl
import ru.kaiten.exception.GetConfigException

@Service
class ConfigurationService(private val webClient: WebClient){

    @Value("\${service.config.url}")
    private lateinit var configURL: String

    fun fetchKaitenConfig(): List<KaitenUrl> {
        val response = webClient.get()
            .uri(configURL)
            .retrieve()
            .bodyToMono(ConfigResponse::class.java)
            .block() ?: throw GetConfigException("Failed to fetch configuration")

        return response.kaitenUrls
    }
}


package ru.kaiten.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import ru.kaiten.config.ConfigKey
import ru.kaiten.dto.ConfigResponse
import ru.kaiten.dto.KaitenUrl
import ru.kaiten.exception.GetConfigException

@Service
class ConfigurationService(private val webClient: WebClient){

    fun fetchKaitenConfig(): List<KaitenUrl> {
        val response = webClient.get()
            .uri(ConfigKey.ServiceConfigUrl.key)
            .retrieve()
            .bodyToMono(ConfigResponse::class.java)
            .block() ?: throw GetConfigException("Failed to fetch configuration")

        return response.kaitenUrls
    }
}


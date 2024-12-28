package ru.kaiten.service

import org.springframework.core.io.ByteArrayResource
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import ru.kaiten.entity.CreateTicketResponse
import ru.kaiten.entity.KaitenUrl
import ru.kaiten.exception.CreateTicketException
import ru.kaiten.exception.EntityIllegalArgumentException
import ru.kaiten.exception.KaitenException

@Service
class TicketService(private val webClient: WebClient) {

    fun processCreatingTickets(
        listKaitenUrl: List<KaitenUrl>,
        title: String,
        description: String,
        files: List<MultipartFile>
    ): CreateTicketResponse {
        val sortedKaitenUrls = listKaitenUrl.sortedByDescending { it.primary }

        val primaryKaitenUrl = sortedKaitenUrls.firstOrNull() { it.primary }
            ?: throw EntityIllegalArgumentException("Primary kaiten URL not found")

        val cardId = createTicketBug(
            kaitenUrl = primaryKaitenUrl,
            title = title,
            description = description,
        ).also {
            attachFile(
                cardId = it,
                files = files,
                kaitenUrl = primaryKaitenUrl
            )
        }

        val ticketUrl = "${primaryKaitenUrl.url}/ticket/$cardId"

        sortedKaitenUrls.filterNot { it.primary }.forEach { kaitenUrl ->
            createTicketChildTask(kaitenUrl = kaitenUrl, cardId = cardId)
        }

        return CreateTicketResponse(ticketUrl = ticketUrl)
    }

    private fun createTicketChildTask(
        kaitenUrl: KaitenUrl,
        cardId: Int
    ) {
        try {
            webClient.post()
                .uri("${kaitenUrl.url}/api/latest/cards/$cardId/children")
                .headers { headers ->
                    headers.setBearerAuth(kaitenUrl.token)
                }
        } catch (ex: WebClientResponseException) {
            throw CreateTicketException("Failed to create child ticket task")
        }
    }

    private fun createTicketBug(
        kaitenUrl: KaitenUrl,
        title: String,
        description: String
    ): Int {
        return try {
            val response = webClient.post()
                .uri("${kaitenUrl.url}/api/latest/cards")
                .headers { headers ->
                    headers.setBearerAuth(kaitenUrl.token)
                }
                .bodyValue(
                    mapOf(
                        "title" to title,
                        "description" to description,
                        "board_id" to kaitenUrl.boardId
                    )
                )
                .retrieve()
                .bodyToMono(Map::class.java)
                .block()

           response?.get("id")?.toString()?.toInt() ?: throw EntityIllegalArgumentException("Id card not found")
        }catch (e: WebClientResponseException) {
            throw CreateTicketException("Failed to create ticket bug")
        }
    }

    private fun attachFile(
        cardId: Int,
        files: List<MultipartFile>,
        kaitenUrl: KaitenUrl
    ) {
        try {
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
                .uri("${kaitenUrl.url}/api/latest/cards/$cardId/files")
                .headers { headers ->
                    headers.setBearerAuth(kaitenUrl.token)
                }
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
        } catch (e: WebClientResponseException) {
            throw KaitenException("Failed to attach file: ${e.message}", e.statusCode)
        }
    }
}
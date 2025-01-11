package ru.kaiten.service.impl

import org.springframework.web.multipart.MultipartFile
import ru.kaiten.client.factory.TicketClientFactory
import ru.kaiten.dto.TomlConfig
import ru.kaiten.dto.CreateCardRequest
import ru.kaiten.dto.CreateCardResponse
import ru.kaiten.service.BoardService
import ru.kaiten.service.SpaceService
import ru.kaiten.service.CardProcessor
import ru.kaiten.service.util.ValidationUtil

class CardProcessorImpl(
    private val clientFactory: TicketClientFactory,
    private val spaceService: SpaceService,
    private val boardService: BoardService,
): CardProcessor {
    override fun processCreateTicket(
        tomlConfig: TomlConfig,
        systemType: String,
        token: String,
        title: String,
        description: String,
        files: List<MultipartFile>
    ): CreateCardResponse {
        ValidationUtil.checkNotEmpty(tomlConfig.defaultUrl, "tomlConfig.defaultUrl")
        ValidationUtil.checkNotEmpty(systemType, "systemType")
        ValidationUtil.checkNotEmpty(token, "token")
        ValidationUtil.checkNotEmpty(title, "title")
        ValidationUtil.checkNotEmpty(description, "description")
        ValidationUtil.checkNotNull(files, "files")

        val spaceId = spaceService.createSpace(
            url = tomlConfig.defaultUrl,
            token = token
        )

        val boardId = boardService.createBoard(
            title = "board$spaceId",
            token = token,
            spaceId = spaceId,
            url = tomlConfig.defaultUrl
        )

        val client = clientFactory.getClient(systemType)
        val request = CreateCardRequest(
            title = title,
            description = description,
            boardId = boardId,
            deadline = "2025-01-30T12:39:45.509Z",
            type = "card"
        )

        val cardId = client.createCard(
            url = tomlConfig.defaultUrl,
            token = token,
            bodyRequest = request
        ).also {
            client.attachFiles(
                url = tomlConfig.defaultUrl,
                cardId = it,
                token = token,
                files = files
            )
        }

        client.createChildCard(
            url = tomlConfig.defaultUrl,
            token = token,
            parentCardId = cardId
        )

        return CreateCardResponse("${tomlConfig.defaultUrl}/ticket/$cardId")
    }
}

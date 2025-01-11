package ru.kaiten.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.kaiten.dto.CreateCardResponse
import ru.kaiten.dto.TomlConfig
import ru.kaiten.service.AuthService
import ru.kaiten.service.CardProcessor

@RestController
@RequestMapping("/api")
class KaitenController(
    private val cardProcessor: CardProcessor,
    private val authService: AuthService,
    private val tomlConfig: TomlConfig
) {
    @PostMapping("/tickets", consumes = ["multipart/form-data"])
    fun createTicket(
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        @RequestParam("files") files: List<MultipartFile>
    ): CreateCardResponse {
        val config = tomlConfig
        val token = authService.authenticate(config)
        return cardProcessor.processCreateTicket(
            tomlConfig = config,
            systemType = "kaiten",
            title = title,
            description = description,
            token = token,
            files = files
        )
    }
}
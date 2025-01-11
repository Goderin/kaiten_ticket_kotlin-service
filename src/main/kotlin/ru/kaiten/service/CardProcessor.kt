package ru.kaiten.service

import org.springframework.web.multipart.MultipartFile
import ru.kaiten.dto.TomlConfig
import ru.kaiten.dto.CreateCardResponse

interface CardProcessor {
    fun processCreateTicket(
        tomlConfig: TomlConfig,
        systemType: String,
        token: String,
        title: String,
        description: String,
        files: List<MultipartFile>
    ): CreateCardResponse
}
package ru.kaiten.client

import org.springframework.web.multipart.MultipartFile
import ru.kaiten.dto.CreateCardRequest

interface CardClient {
    fun createCard(url: String, token: String, bodyRequest: CreateCardRequest): Int
    fun attachFiles(url: String, token: String, cardId: Int,  files: List<MultipartFile>)
    fun createChildCard(url: String, token: String, parentCardId: Int)
}
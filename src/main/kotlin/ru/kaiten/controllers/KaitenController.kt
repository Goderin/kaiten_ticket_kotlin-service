package ru.kaiten.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.kaiten.dto.CreateTicketResponse
import ru.kaiten.service.ConfigurationService
import ru.kaiten.service.TicketService

@RestController
@RequestMapping("/api")
class KaitenController(
    private val configService: ConfigurationService,
    private val ticketService: TicketService
) {

    @PostMapping("/tikets", consumes = ["multipart/form-data"])
    fun createTicket(
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        @RequestParam("files") files: List<MultipartFile>
    ): CreateTicketResponse {
        val listKaitenUrl = configService.fetchKaitenConfig()
        return ticketService.processCreatingTickets(
            listKaitenUrl = listKaitenUrl,
            title = title,
            description = description,
            files = files
        )
    }
}
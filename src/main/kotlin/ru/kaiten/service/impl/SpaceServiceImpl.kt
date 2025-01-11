package ru.kaiten.service.impl

import ru.kaiten.client.SpaceClient
import ru.kaiten.service.SpaceService
import ru.kaiten.service.util.ValidationUtil
import java.util.UUID

class SpaceServiceImpl(private val spaceClient: SpaceClient): SpaceService {
    override fun createSpace(url: String, token: String): Int {
        ValidationUtil.checkNotEmpty(url, "url")
        ValidationUtil.checkNotEmpty(token, "token")

        //пока рандомный
        val title = UUID.randomUUID().toString().substring(0, 10)

        return spaceClient.createSpace(
            url = url,
            token =token,
            title = title
        )
    }
}
package ru.kaiten.service.impl

import ru.kaiten.client.BoardClient
import ru.kaiten.service.BoardService
import ru.kaiten.service.util.ValidationUtil

class BoardServiceImpl(private val boardClient: BoardClient) : BoardService {
    override fun createBoard(title: String, token: String, spaceId: Int, url: String): Int {
        ValidationUtil.checkNotEmpty(title, "title")
        ValidationUtil.checkNotEmpty(token, "token")
        ValidationUtil.checkNotNull(spaceId, "spaceId")
        ValidationUtil.checkNotEmpty(url, "url")

        return boardClient.createBoard(
            url = url,
            token = token,
            spaceId = spaceId,
            title = title,
        )
    }
}
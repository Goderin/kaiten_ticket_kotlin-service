package ru.kaiten.client

interface BoardClient {
    fun createBoard(url: String, token: String, title: String, spaceId: Int): Int
}
package ru.kaiten.service

interface BoardService {
    fun createBoard(title: String, token: String, spaceId: Int, url: String): Int
}
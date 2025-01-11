package ru.kaiten.service

interface SpaceService {
    fun createSpace(url: String, token: String): Int
}
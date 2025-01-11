package ru.kaiten.client

interface SpaceClient {
    fun createSpace(url: String, token: String, title: String): Int
}
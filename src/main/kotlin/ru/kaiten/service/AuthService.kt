package ru.kaiten.service

import ru.kaiten.dto.TomlConfig

interface AuthService {
    fun authenticate(config: TomlConfig): String
}
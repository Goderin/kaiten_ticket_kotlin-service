package ru.kaiten.config

sealed class ConfigKey(val key: String) {
    object ServiceConfigUrl: ConfigKey("https://test.born-in-july.ru/api/settings")
}
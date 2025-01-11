package ru.kaiten.service.util

import ru.kaiten.exception.InvalidArgumentException

object ValidationUtil {
    fun checkNotEmpty(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw InvalidArgumentException("$fieldName can not be empty!")
        }
    }

    fun checkNotNull(value: Any?, fieldName: String) {
        if (value == null) {
            throw InvalidArgumentException("$fieldName can not be null!")
        }
    }
}
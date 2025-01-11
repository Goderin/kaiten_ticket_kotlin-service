package ru.kaiten.client

import org.springframework.util.MultiValueMap

interface AuthClient {
    fun authenticate(url: String, formData: MultiValueMap<String, String>): String
}
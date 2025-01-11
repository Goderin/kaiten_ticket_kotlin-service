package ru.kaiten.service.util

import com.macasaet.fernet.Key
import com.macasaet.fernet.Token
import com.macasaet.fernet.StringValidator
import com.macasaet.fernet.Validator
import java.time.Duration
import java.time.temporal.TemporalAmount

class FernetDecoder(private val key: String) {
    fun decrypt(encryptedData: String): String {
        val token = Token.fromString(encryptedData)
        val validator: Validator<String> = object : StringValidator {
            override fun getTimeToLive(): TemporalAmount {
                return Duration.ofHours(24)
            }
        }
       return token.validateAndDecrypt(Key(key), validator)
    }
}
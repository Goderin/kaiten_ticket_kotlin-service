package ru.kaiten.service.impl

import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import ru.kaiten.client.AuthClient
import ru.kaiten.dto.TomlConfig
import ru.kaiten.service.AuthService
import ru.kaiten.service.util.FernetDecoder
import ru.kaiten.service.util.ValidationUtil

class AuthServiceImpl(private val authClient: AuthClient) : AuthService {
    override fun authenticate(config: TomlConfig): String {
        ValidationUtil.checkNotEmpty(config.authUrl, "config.authUrl")
        ValidationUtil.checkNotEmpty(config.fernetKey, "config.fernetKey")
        ValidationUtil.checkNotEmpty(config.clientSecret, "config.clientSecret")
        ValidationUtil.checkNotEmpty(config.clientId, "config.clientId")

        val decoder = FernetDecoder(config.fernetKey)
        val clientSecretDecrypt = decoder.decrypt(config.clientSecret)

        val formData: MultiValueMap<String, String> = LinkedMultiValueMap()
        formData.add("grant_type", "client_credentials")
        formData.add("client_id", config.clientId)
        formData.add("client_secret", clientSecretDecrypt)

        return authClient.authenticate(url = config.authUrl, formData = formData)
    }
}
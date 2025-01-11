package ru.kaiten.config

import com.moandjiezana.toml.Toml
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import ru.kaiten.client.AuthClient
import ru.kaiten.client.BoardClient
import ru.kaiten.client.SpaceClient
import ru.kaiten.client.CardClient
import ru.kaiten.client.factory.TicketClientFactory
import ru.kaiten.client.impl.AuthClientImpl
import ru.kaiten.client.impl.BoardClientImpl
import ru.kaiten.client.impl.KaitenClient
import ru.kaiten.client.impl.SpaceClientImpl
import ru.kaiten.dto.TomlConfig
import ru.kaiten.service.AuthService
import ru.kaiten.service.BoardService
import ru.kaiten.service.SpaceService
import ru.kaiten.service.CardProcessor
import ru.kaiten.service.impl.AuthServiceImpl
import ru.kaiten.service.impl.BoardServiceImpl
import ru.kaiten.service.impl.SpaceServiceImpl
import ru.kaiten.service.impl.CardProcessorImpl
import java.io.InputStream

@Configuration
open class AppConfig {

    @Value("\${config.path}")
    lateinit var configPath: String

    @Bean
    open fun tomlConfig(): TomlConfig {
        val inputStream: InputStream = this::class.java.classLoader.getResourceAsStream(configPath)
            ?: throw IllegalStateException("Configuration file not found at $configPath")

        val toml = Toml().read(inputStream)
        return TomlConfig(
            defaultUrl = toml.getString("settings.default_endpoint"),
            fernetKey = toml.getString("settings.fernet_key"),
            authUrl = toml.getString("settings.auth_url"),
            profileUrl = toml.getString("settings.profile_url"),
            clientId = toml.getString("settings.client_id"),
            clientSecret = toml.getString("settings.client_secret")
        )
    }

    @Bean
    open fun webClient(): WebClient {
        return WebClient.builder().build()
    }

    @Bean
    open fun kaitenClient(webClient: WebClient): CardClient {
        return KaitenClient(webClient)
    }

    @Bean
    open fun ticketClientFactory(kaitenClient: CardClient): TicketClientFactory {
        return TicketClientFactory(
            mapOf(
                "kaiten" to kaitenClient
            )
        )
    }

    @Bean
    open fun authClient(webClient: WebClient): AuthClient {
        return AuthClientImpl(webClient)
    }

    @Bean
    open fun boardClient(webClient: WebClient): BoardClient {
        return BoardClientImpl(webClient)
    }

    @Bean
    open fun spaceClient(webClient: WebClient): SpaceClient {
        return SpaceClientImpl(webClient)
    }

    @Bean
    open fun boardService(boardClient: BoardClient): BoardService {
        return BoardServiceImpl(boardClient)
    }

    @Bean
    open fun spaceService(spaceClient: SpaceClient): SpaceService {
        return SpaceServiceImpl(spaceClient)
    }

    @Bean
    open fun authService(authClient: AuthClient): AuthService {
        return AuthServiceImpl(authClient)
    }

    @Bean
    open fun ticketProcessor(
        clientFactory: TicketClientFactory,
        spaceService: SpaceService,
        boardService: BoardService
    ): CardProcessor {
        return CardProcessorImpl(clientFactory, spaceService, boardService)
    }
}
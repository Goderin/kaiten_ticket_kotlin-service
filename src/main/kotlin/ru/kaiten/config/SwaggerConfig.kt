package ru.kaiten.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SwaggerConfig {
    @Bean
    open fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Ticket API")
                    .version("1.0")
                    .description("API для работы с тикетами в системе Kaiten")
            )
    }
}
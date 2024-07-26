package com.goofy.client.discord

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class DiscordClientAutoConfigure {
    private val logger = KotlinLogging.logger { }

    @Bean
    @Primary
    fun discordClient(): DiscordClient {
        logger.info { "initialized DiscordClient" }
        return ReactiveDiscordClient()
    }
}

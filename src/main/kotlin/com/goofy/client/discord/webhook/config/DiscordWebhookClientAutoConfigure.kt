package com.goofy.client.discord.webhook.config

import com.goofy.client.discord.webhook.client.DiscordWebhookClient
import com.goofy.client.discord.webhook.client.ReactiveDiscordWebhookClient
import com.goofy.client.discord.webhook.client.WebClientFactory
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated

@Validated
@Configuration
class DiscordWebhookClientAutoConfigure {
    private val logger = KotlinLogging.logger { }

    @Bean
    @ConditionalOnProperty(prefix = "goofy.client.discord.webhook", name = ["url"])
    @ConfigurationProperties(prefix = "goofy.client.discord.webhook")
    fun discordWebhookConfig() = Config()

    @Bean
    @ConditionalOnBean(name = ["discordWebhookConfig"])
    @ConditionalOnMissingBean(DiscordWebhookClient::class)
    fun discordWebhookClient(
        @Valid discordWebhookConfig: Config
    ): DiscordWebhookClient {
        logger.info { "initialized DiscordClient. $discordWebhookConfig" }

        val webclient = WebClientFactory.generate(discordWebhookConfig.url)

        return ReactiveDiscordWebhookClient(webclient, discordWebhookConfig)
    }

    data class Config(
        @field:NotBlank
        var url: String = "",
        var credential: Map<Int, WebhookContext> = emptyMap()
    ) {
        data class WebhookContext(
            @field:NotBlank
            var webhookId: String = "",
            @field:NotBlank
            var webhookToken: String = "",
        )
    }
}

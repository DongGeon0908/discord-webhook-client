package com.goofy.client.discord.webhook

import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class ReactiveDiscordWebhookClient(
    client: WebClient,
    private val config: DiscordWebhookClientAutoConfigure.Config,
) : DiscordWebhookClient, ReactiveClient(client) {
    override fun getWebhookWithToken(id: Int): Mono<GetWebhookWithTokenResponse> {
        val credential = config.credential[id] ?: throw RuntimeException("Webhook Credential does not exist, $id")

        return client
            .get()
            .uri("/${credential.webhookId}/${credential.webhookToken}")
            .request()
    }
}

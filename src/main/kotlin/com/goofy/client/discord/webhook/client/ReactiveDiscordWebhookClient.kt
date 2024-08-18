package com.goofy.client.discord.webhook.client

import com.goofy.client.discord.webhook.config.DiscordWebhookClientAutoConfigure
import com.goofy.client.discord.webhook.model.request.SendMessageRequest
import com.goofy.client.discord.webhook.model.response.GetWebhookWithTokenResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class ReactiveDiscordWebhookClient(
    client: WebClient,
    private val config: DiscordWebhookClientAutoConfigure.Config,
) : DiscordWebhookClient, ReactiveClient(client) {
    override fun getWebhookWithToken(id: Int): Mono<GetWebhookWithTokenResponse> {
        return client
            .get()
            .discordUri(id)
            .request()
    }

    override fun sendMessage(id: Int, request: SendMessageRequest): Mono<Void> {
        return client
            .post()
            .discordUri(id)
            .bodyValue(request)
            .request()
    }

    private fun WebClient.RequestHeadersUriSpec<*>.discordUri(id: Int): WebClient.RequestHeadersSpec<*> {
        val credential = getWebhookCredential(id)
        return this.uri("/${credential.webhookId}/${credential.webhookToken}")
    }

    private fun WebClient.RequestBodyUriSpec.discordUri(id: Int): WebClient.RequestBodySpec {
        val credential = getWebhookCredential(id)
        return this.uri("/${credential.webhookId}/${credential.webhookToken}")
    }

    private fun getWebhookCredential(id: Int): DiscordWebhookClientAutoConfigure.Config.WebhookContext {
        return config.credential[id] ?: throw RuntimeException("Webhook Credential does not exist, $id")
    }
}

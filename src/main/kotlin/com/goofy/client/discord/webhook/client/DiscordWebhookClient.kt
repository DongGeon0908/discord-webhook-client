package com.goofy.client.discord.webhook.client

import com.goofy.client.discord.webhook.model.request.SendMessageRequest
import com.goofy.client.discord.webhook.model.response.GetWebhookWithTokenResponse
import reactor.core.publisher.Mono

interface DiscordWebhookClient {
    fun getWebhookWithToken(id: Int): Mono<GetWebhookWithTokenResponse>

    fun sendMessage(id: Int, request: SendMessageRequest): Mono<Void>
}

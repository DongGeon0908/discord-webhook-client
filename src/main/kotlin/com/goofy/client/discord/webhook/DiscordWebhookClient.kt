package com.goofy.client.discord.webhook

import reactor.core.publisher.Mono

interface DiscordWebhookClient {
    fun getWebhookWithToken(id: Int): Mono<GetWebhookWithTokenResponse>
}

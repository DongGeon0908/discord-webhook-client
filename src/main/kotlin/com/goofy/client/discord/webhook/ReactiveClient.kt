package com.goofy.client.discord.webhook

import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

abstract class ReactiveClient(
    protected val client: WebClient,
) {
    protected inline fun <reified T : Any> WebClient.RequestHeadersSpec<*>.request(): Mono<T> {
        return this.retrieve().bodyToMono()
    }
}

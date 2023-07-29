package com.ektour.utils

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class SlackClient(
    private val restTemplate: RestTemplate,
    @Value("\${slack.webhook.url}") private val webhookUrl: String,
) {

    data class SlackMessage(
        val username: String,
        val text: String,
        @JsonProperty("icon_emoji")
        val iconEmoji: String,
        val channel: String
    )

    @Async
    fun sendSlack(
        senderName: String = "logger",
        message: String,
        iconEmoji: String = ":eyes:",
        channel: String
    ) {
        val slackMessage = SlackMessage(
            username = senderName,
            text = message,
            iconEmoji = iconEmoji,
            channel = channel
        )
        val entity = HttpEntity<SlackMessage>(slackMessage)
        restTemplate.exchange(webhookUrl, HttpMethod.POST, entity, String::class.java)
    }
}

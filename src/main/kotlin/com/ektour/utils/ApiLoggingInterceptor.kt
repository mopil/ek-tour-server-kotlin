package com.ektour.utils

import com.ektour.common.MultiAccessRequestWrapper
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ApiLoggingInterceptor(
    private val slackClient: SlackClient,
    private val converter: JsonPrettyViewConverter
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        var message = """
            $timestamp - ${request.getIp()} ${request.method} ${request.requestURL}
        """.trimIndent()
        if (request.contentType != null &&
            request.contentType.startsWith("application/json") &&
            (
                request.method.startsWith("POST") ||
                    request.method.startsWith("PUT")
                ) &&
            request is MultiAccessRequestWrapper
        ) {
            val body = converter.convert(request.getContents())
            message += "BODY\n$body"
        }

        if (request.contentType != null &&
            request.contentType.startsWith("multipart/form-data") &&
            request.method.startsWith("POST") &&
            request is MultiAccessRequestWrapper
        ) {
            val body = request.getContents().toString()
            message += "BODY\n$body"
        }

        slackClient.sendSlack(message = message, channel = "api-log")

        return super.preHandle(request, response, handler)
    }
}

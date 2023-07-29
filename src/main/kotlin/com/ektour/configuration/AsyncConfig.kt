package com.ektour.configuration

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException


@Configuration
@EnableAsync
class AsyncConfig : AsyncConfigurer {
    val log = logger()

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
        return AsyncUncaughtExceptionHandler { throwable, method, params ->
            if (throwable is HttpClientErrorException || throwable is HttpServerErrorException) {
                log.warn("method:${method.name} params:${params.toList()} handleAsyncSlackException: ${throwable.message}")
            }
        }
    }
}



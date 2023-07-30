package com.ektour.common.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Logger {
    private val logger: Logger get() = LoggerFactory.getLogger(this::class.java)

    fun error(message: String, e: Throwable? = null) =
        if (e != null) logger.error(message, e) else logger.error(message)

    fun warn(message: String, e: Throwable? = null) = if (e != null) logger.warn(message, e) else logger.warn(message)

    fun debug(message: String, e: Throwable? = null) =
        if (e != null) logger.debug(message, e) else logger.debug(message)

    fun info(message: String, e: Throwable? = null) = if (e != null) logger.info(message, e) else logger.info(message)
}

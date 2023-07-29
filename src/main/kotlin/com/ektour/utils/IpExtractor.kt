package com.ektour.utils

import javax.servlet.http.HttpServletRequest

object IpExtractor {
    fun HttpServletRequest.getIp(): String =
        this.getHeader("X-Forwarded-For")
            ?: this.getHeader("Proxy-Client-IP")
            ?: this.getHeader("WL-Proxy-Client-IP")
            ?: this.getHeader("HTTP_CLIENT_IP")
            ?: this.getHeader("HTTP_X_FORWARDED_FOR")
            ?: this.remoteAddr
}

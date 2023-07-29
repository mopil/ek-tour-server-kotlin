package com.ektour.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}

const val ADMIN = "admin"

fun getLogoPath(): String {
    return if (System.getProperty("os.name").contains("Win")) {
        "C:\\Users\\ek\\Desktop\\spring\\logo.png"
    } else {
        "/home/ubuntu/spring/logo.png"
    }
}

fun getExelPath(): String {
    return if (System.getProperty("os.name").contains("Win")) {
        "C:\\dev\\estimate.xlsx"
    } else {
        "/home/ubuntu/spring/estimate.xlsx"
    }
}

fun getFilePath(): String {
    return if (System.getProperty("os.name").contains("Win")) {
        "C:\\dev\\spring\\"
    } else {
        "file:///home/ubuntu/spring/"
    }
}

class AdminException(message: String) : RuntimeException(message)
class EmailException(message: String) : RuntimeException(message)
class ExcelException(message: String) : RuntimeException(message)

const val ADMIN_EMAIL_ACCOUNT = "ektour0914@naver.com"

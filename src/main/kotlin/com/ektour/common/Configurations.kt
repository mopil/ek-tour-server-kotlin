package com.ektour.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}

const val ADMIN = "admin"
const val LINUX_LOGO_PATH = "/home/ubuntu/spring/logo.png";
//const val LINUX_EXCEL_PATH = "/home/ubuntu/spring/estimate.xlsx"
const val LINUX_EXCEL_PATH = "C:\\dev\\estimate.xlsx"


class AdminException(message: String): RuntimeException(message)
class EmailException(message: String): RuntimeException(message)
class ExcelException(message: String): RuntimeException(message)

const val ADMIN_EMAIL_ACCOUNT = "ektour0914@naver.com"
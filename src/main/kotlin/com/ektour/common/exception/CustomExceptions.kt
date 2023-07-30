package com.ektour.common.exception

class AdminException(override val message: String) : RuntimeException(message)
class ExcelException(override val message: String) : RuntimeException(message)

package com.ektour.common

object PathFinder {
    private fun isWindow(): Boolean {
        return System.getProperty("os.name").contains("Win")
    }
    fun getLogoPath(): String {
        return if (isWindow()) {
            "C:\\Users\\ek\\Desktop\\spring\\logo.png"
        } else {
            "/home/ubuntu/spring/logo.png"
        }
    }

    fun getExelPath(): String {
        return if (isWindow()) {
            "C:\\dev\\estimate.xlsx"
        } else {
            "/home/ubuntu/spring/estimate.xlsx"
        }
    }

    fun getFilePath(): String {
        return if (isWindow()) {
            "C:\\dev\\spring\\"
        } else {
            "file:///home/ubuntu/spring/"
        }
    }
}

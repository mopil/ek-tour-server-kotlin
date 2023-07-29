package com.ektour.web

object StaticPages {
    const val ADMIN_MAIN = "mainPage"
    const val ADMIN_SETTING = "settingPage"
    const val ESTIMATE_DETAIL = "estimateDetailPage"
    const val ESTIMATE_SEARCH = "searchPage"
    const val ADMIN_LOGIN = "loginPage"
    const val CLIENT_ERROR_PAGE = "error/errorPage"
    const val ADMIN_ERROR_PAGE = "error/adminErrorPage"

    object Redirect {
        const val ADMIN = "redirect:/admin"
        const val ADMIN_MAIN = "redirect:/admin/main"
        const val ADMIN_SETTING = "redirect:/admin/setting"
    }
}
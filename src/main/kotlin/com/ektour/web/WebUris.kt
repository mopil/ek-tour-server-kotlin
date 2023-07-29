package com.ektour.web

object WebUris {
    object Admin {
        private const val ADMIN = "/admin"
        const val LOGIN = "$ADMIN/login"
        const val LOGOUT = "$ADMIN/logout"

        const val GO_ADMIN_SETTING_PAGE = "$ADMIN/setting"
        const val UPDATE_ADMIN_INFO = "$ADMIN/setting/info"
        const val UPDATE_ADMIN_PASSWORD = "$ADMIN/setting/password"
        const val UPDATE_ADMIN_COMPANY_LOGO = "$ADMIN/logo"

        const val GO_ADMIN_MAIN_PAGE = "$ADMIN/main"
        const val ESTIMATE_FIRST_SEARCH = "$ADMIN/search"
        const val SEARCHING_WITH_PAGING = "$ADMIN/search"
        const val GO_ESTIMATE_DETAIL_PAGE = "$ADMIN/estimate/{id}"
        const val DOWNLOAD_EXCEL = "$ADMIN/estimate/{id}/excel"
        const val UPDATE_ESTIMATE = "$ADMIN/update/estimate/{id}"
        const val HARD_DELETE_ESTIMATE = "$ADMIN/delete/estimate/{id}"
    }
}

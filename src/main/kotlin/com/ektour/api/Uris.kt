package com.ektour.api

object Uris {
    object EstimateApis {
        private const val ESTIMATE = "/estimate"
        const val CREATE_ESTIMATE = ESTIMATE
        const val GET_ESTIMATE_WITH_PHONE_AND_PASSWORD = "$ESTIMATE/{id}"
        const val GET_ESTIMATE_WITHOUT_FORM = "$ESTIMATE/{id}"
        const val GET_ALL_ESTIMATES_BY_PAGING = "$ESTIMATE/all"
        const val GET_ALL_PAGE_COUNT = "$ESTIMATE/all/page"
        const val GET_ALL_MY_ESTIMATES_WITHOUT_PAGING = "$ESTIMATE/search/my"
        const val GET_ALL_MY_ESTIMATES_WITH_PAGING = "$ESTIMATE/search/my/all"
        const val UPDATE_MY_ESTIMATE = "$ESTIMATE/{id}"
        const val SOFT_DELETE_MY_ESTIMATE = "$ESTIMATE/{id}"
        const val SEND_NAVER_MAIL = "$ESTIMATE/alarm"
    }

    object AdminApis {
        private const val ADMIN = "/admin"
        const val GET_ADMIN_INFO = "$ADMIN/info"
    }
}

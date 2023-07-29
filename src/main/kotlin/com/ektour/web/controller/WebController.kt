package com.ektour.web.controller

import com.ektour.service.VisitLogService
import com.ektour.web.StaticPages
import javax.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import springfox.documentation.annotations.ApiIgnore

@Controller
@ApiIgnore
class WebController(private val visitLogService: VisitLogService) {
    companion object {
        const val FORWARD_INDEX_HTML = "forward:/index.html"
    }
    @GetMapping(
        value = [
            "/estimate/list/**",
            "/notice",
            "/list",
            "/introduce",
            "/bus",
            "/request",
            "/my",
            "/estimate/my/list/**",
            "/search/my/estimate",
            "/service-center",
            "/mobile",
            "/mobile/**"
        ]
    )
    fun redirectFrontendRouting() = FORWARD_INDEX_HTML

    @GetMapping
    fun saveVisitLogAndRedirectMainFrontendPage(request: HttpServletRequest): String {
        visitLogService.saveVisitLog(request)
        return FORWARD_INDEX_HTML
    }

    @GetMapping("/admin")
    fun redirectAdminEntrancePage() = StaticPages.Redirect.ADMIN_MAIN
}

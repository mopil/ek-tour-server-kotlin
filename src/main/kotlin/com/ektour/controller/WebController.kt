package com.ektour.controller

import com.ektour.service.VisitLogService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import springfox.documentation.annotations.ApiIgnore
import javax.servlet.http.HttpServletRequest

@Controller
@ApiIgnore
class WebController(private val visitLogService: VisitLogService) {
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
    fun redirectFrontendRouting() = "forward:/index.html"

    @GetMapping
    fun saveVisitLogAndRedirectMainFrontendPage(request: HttpServletRequest): String {
        visitLogService.saveVisitLog(request)
        return "forward:/index.html"
    }

    @GetMapping("/admin")
    fun redirectAdminEntrancePage() = "redirect:/admin/main"
}

package com.ektour.controller

import com.ektour.service.VisitService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController(private val visitService: VisitService) {
    /**
     * 리액트 라우팅 리다이렉션
     */
    @GetMapping(value = [
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
    ])
    fun redirect() = "forward:/index.html"
    
    @GetMapping
    fun mainPage(): String {
        visitService.visit() // 방문자 기록
        return "forward:/index.html"
    }
}
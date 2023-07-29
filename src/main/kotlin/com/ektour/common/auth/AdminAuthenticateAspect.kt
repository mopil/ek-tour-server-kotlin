package com.ektour.common.auth

import com.ektour.configuration.ADMIN
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.security.auth.login.LoginException

@Aspect
@Component
class AdminAuthenticateAspect {
    @Before("@within(AdminAuthenticate)")
    fun authCheck() {
        val request = RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes
        request.request.session.getAttribute(ADMIN) ?: throw LoginException("로그인 되어 있지 않음.")
    }
}

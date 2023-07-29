package com.ektour.common.exception

import com.ektour.api.dto.ErrorListResponse
import com.ektour.api.dto.ErrorResponse
import com.ektour.common.Logger
import com.ektour.web.StaticPages
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.security.auth.login.LoginException

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BindException::class)
    fun validationFailHandle(ex: BindException): ResponseEntity<ErrorListResponse> {
        Logger.warn(
            "[${ex.javaClass.simpleName}] : ${ex.message}, \nerrors = ${ex.bindingResult.fieldErrors}"
        )

        val errors = ex.bindingResult.fieldErrors
        val body = ErrorListResponse(
            errors.map {
                ErrorResponse("유효성 검증 실패.", it.field + " / " + it.defaultMessage)
            }
        )
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(body)
    }

    companion object {
        const val ERROR_MESSAGE = "errorMessage"
    }

    @ExceptionHandler(Exception::class)
    fun redirectErrorPage(ex: Exception, model: Model): String {
        Logger.warn("[${ex.javaClass.simpleName}] handled: ${ex.message}")
        model[ERROR_MESSAGE] = ex.message ?: ""
        return StaticPages.CLIENT_ERROR_PAGE
    }

    @ExceptionHandler(AdminException::class)
    fun redirectAdminErrorPage(ex: Exception, model: Model): String {
        Logger.warn("[${ex.javaClass.simpleName}] handled: ${ex.message}")
        model[ERROR_MESSAGE] = ex.message ?: ""
        return StaticPages.ADMIN_ERROR_PAGE
    }

    @ExceptionHandler(LoginException::class)
    fun loginExHandler(ex: LoginException) = StaticPages.ADMIN_LOGIN
}

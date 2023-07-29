package com.ektour.common

import com.ektour.configuration.logger
import com.ektour.dto.ErrorListResponse
import com.ektour.dto.ErrorResponse
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

    val log = logger()

    @ExceptionHandler(BindException::class)
    fun validationFailHandle(ex: BindException): ResponseEntity<ErrorListResponse> {
        log.warn(
            "[{}] : {}, \nerrors = {}",
            ex.javaClass.simpleName,
            ex.message,
            ex.bindingResult.fieldErrors
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

    @ExceptionHandler(Exception::class)
    fun redirectErrorPage(ex: Exception, model: Model): String {
        log.warn("[{}] handled: {}", ex.javaClass.simpleName, ex.message)
        model["errorMessage"] = ex.message ?: ""
        return "error/errorPage"
    }

    @ExceptionHandler(LoginException::class)
    fun loginExHandler(ex: LoginException) = "loginPage"
}

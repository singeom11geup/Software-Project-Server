package com.rangjin.software_project_server.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(BaseException::class)
    protected fun handleBaseException(e: BaseException): ResponseEntity<Any> {
        return ResponseEntity.status(e.baseResponseCode.status).body(e.baseResponseCode.message)
    }
}
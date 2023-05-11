package com.rangjin.software_project_server.exception

import org.springframework.http.HttpStatus

enum class BaseResponseCode(val status: HttpStatus, val message: String) {
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입니다"),
}
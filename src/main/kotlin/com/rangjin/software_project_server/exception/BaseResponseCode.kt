package com.rangjin.software_project_server.exception

import org.springframework.http.HttpStatus

enum class BaseResponseCode(val status: HttpStatus, val message: String) {
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 id를 가진 카메라를 찾을 수 없습니다"),
}
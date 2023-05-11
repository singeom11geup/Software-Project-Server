package com.rangjin.software_project_server.dto.camera

data class LoginRequestDto(
    val username: String,
    val password: String,
) {
    constructor(): this("", "")
}
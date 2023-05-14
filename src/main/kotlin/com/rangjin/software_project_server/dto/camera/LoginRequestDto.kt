package com.rangjin.software_project_server.dto.camera

data class LoginRequestDto(
    val name: String,
    val password: String,
) {
    constructor(): this("", "")
}
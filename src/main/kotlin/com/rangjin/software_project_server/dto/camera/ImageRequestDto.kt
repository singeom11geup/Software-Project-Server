package com.rangjin.software_project_server.dto.camera

data class ImageRequestDto(
    val password: String,
    val state: Int,
    val image: String,
) {
    constructor(): this("", -1, "")
}
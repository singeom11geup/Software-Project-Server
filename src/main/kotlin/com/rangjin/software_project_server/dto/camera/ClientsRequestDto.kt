package com.rangjin.software_project_server.dto.camera

import com.rangjin.software_project_server.domain.Camera

data class ClientsRequestDto(
    val clients: List<Camera.Client>,
) {
    constructor(): this(emptyList())
}
package com.rangjin.software_project_server.dto.camera

import com.rangjin.software_project_server.domain.Camera

data class ClientsUpdateRequest(
    val id: Long,
    val clients: List<Camera.Client>,
)
package com.rangjin.software_project_server.dto.camera

import com.rangjin.software_project_server.domain.Camera

data class TablesUpdateRequest(
    val id: Long,
    val tables: List<Camera.Table>,
)
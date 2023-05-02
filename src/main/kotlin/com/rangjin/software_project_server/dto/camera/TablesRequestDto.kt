package com.rangjin.software_project_server.dto.camera

import com.rangjin.software_project_server.domain.Camera

data class TablesRequestDto(
    val tables: List<Camera.Table>,
) {
    constructor(): this(emptyList())
}
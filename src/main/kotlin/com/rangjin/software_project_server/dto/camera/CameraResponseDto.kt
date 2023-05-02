package com.rangjin.software_project_server.dto.camera

import com.rangjin.software_project_server.domain.Camera

data class CameraResponseDto(
    val id: Long?,
    val tables: List<TablesResponseDto>,
    val clients: List<ClientsResponseDto>
) {
    constructor(camera: Camera): this(camera.id,
        camera.tables.map { TablesResponseDto(it) },
        camera.clients.map { ClientsResponseDto(it) })
}
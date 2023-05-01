package com.rangjin.software_project_server.dto.camera

import com.rangjin.software_project_server.domain.Camera

data class CameraResponseDto(
    val id: Long?,
    val tables: List<TableResponseDto>,
    val clients: List<ClientResponseDto>
) {
    constructor(camera: Camera): this(camera.id,
        camera.tables.map { TableResponseDto(it) },
        camera.clients.map { ClientResponseDto(it) }
        )
}
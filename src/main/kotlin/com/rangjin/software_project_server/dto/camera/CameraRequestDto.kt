package com.rangjin.software_project_server.dto.camera

data class CameraRequestDto(
    val tables: TablesRequestDto,
    val clients: ClientsRequestDto,
)
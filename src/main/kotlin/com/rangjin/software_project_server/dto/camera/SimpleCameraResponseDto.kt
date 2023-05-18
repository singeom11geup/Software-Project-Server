package com.rangjin.software_project_server.dto.camera

import com.rangjin.software_project_server.domain.Camera

data class SimpleCameraResponseDto(
    val id: Long?,
    val name: String,
) {

    constructor(camera: Camera): this(
        camera.id,
        camera.name,
    )

}
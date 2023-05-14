package com.rangjin.software_project_server.repository

import com.rangjin.software_project_server.domain.Camera
import org.springframework.data.jpa.repository.JpaRepository

interface CameraRepository: JpaRepository<Camera, Long> {

    fun existsByName(username: String): Boolean
    fun findByName(username: String): Camera

}
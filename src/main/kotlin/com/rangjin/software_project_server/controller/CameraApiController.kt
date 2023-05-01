package com.rangjin.software_project_server.controller

import com.rangjin.software_project_server.service.CameraService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/camera")
class CameraApiController(
    private val cameraService: CameraService,
) {

    @GetMapping("/create")
    fun createCamera(): ResponseEntity<Any> {
        return ResponseEntity.ok().body(cameraService.createCamera())
    }

    @GetMapping("/read/{id}")
    fun getCamera(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok().body(cameraService.getCamera(id))
    }

}
package com.rangjin.software_project_server.controller

import com.rangjin.software_project_server.dto.camera.CameraRequestDto
import com.rangjin.software_project_server.dto.camera.ImageRequestDto
import com.rangjin.software_project_server.service.CameraService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/camera")
class CameraApiController(
    private val cameraService: CameraService,
) {

    @GetMapping("/create")
    fun createCamera(): ResponseEntity<Any> {
        return ResponseEntity.ok().body(cameraService.createCamera())
    }

    @GetMapping("/image/{id}")
    fun getImage(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok().body(cameraService.getImage(id))
    }

    @PostMapping("/image/update/{id}")
    fun image(@PathVariable id: Long, @RequestBody request: ImageRequestDto): ResponseEntity<Any> {
        return ResponseEntity.ok().body(cameraService.updateImage(id, request))
    }

    @GetMapping("/read/{id}")
    fun getCamera(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok().body(cameraService.getCamera(id))
    }

    @PutMapping("/update/camera/{id}")
    fun updateCamera(@PathVariable id: Long, @RequestBody request: CameraRequestDto): ResponseEntity<Any> {
        return ResponseEntity.ok().body(cameraService.updateCamera(id, request))
    }

}
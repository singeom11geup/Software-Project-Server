package com.rangjin.software_project_server.controller

import com.rangjin.software_project_server.dto.camera.ImageRequestDto
import com.rangjin.software_project_server.dto.camera.LoginRequestDto
import com.rangjin.software_project_server.service.CameraService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/camera")
class CameraApiController(
    private val cameraService: CameraService,
) {

    @GetMapping("/login")
    fun loginCamera(@RequestBody request: LoginRequestDto): ResponseEntity<Any> {
        return ResponseEntity.ok().body(cameraService.loginCamera(request))
    }

    @PostMapping("/update/{id}")
    fun image(@PathVariable id: Long, @RequestBody request: ImageRequestDto): ResponseEntity<Any> {
        return ResponseEntity.ok().body(cameraService.updateImage(id, request))
    }

    @GetMapping("/read/{id}")
    fun getCamera(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok().body(cameraService.getCamera(id))
    }

    @GetMapping("/read/all")
    fun getAllCamera(): ResponseEntity<Any> {
        print(cameraService.getAllCamera())
        return ResponseEntity.ok().body(cameraService.getAllCamera())
    }

}
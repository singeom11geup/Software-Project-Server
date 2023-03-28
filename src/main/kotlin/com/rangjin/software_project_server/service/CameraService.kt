package com.rangjin.software_project_server.service

import com.rangjin.software_project_server.domain.Camera
import com.rangjin.software_project_server.dto.camera.TableUpdateRequest
import com.rangjin.software_project_server.repository.CameraRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CameraService(
    private val cameraRepository: CameraRepository,
) {

    @Transactional
    fun createCamera(): Long? {
        return cameraRepository.save(Camera()).id
    }

    @Transactional
    fun saveTables(request: TableUpdateRequest) {
        val camera = cameraRepository.findByIdOrNull(request.id)
        camera?.updateTables(request.tables)
    }

}
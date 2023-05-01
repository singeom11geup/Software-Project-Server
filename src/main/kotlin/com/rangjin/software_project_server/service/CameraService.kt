package com.rangjin.software_project_server.service

import com.rangjin.software_project_server.domain.Camera
import com.rangjin.software_project_server.dto.camera.CameraResponseDto
import com.rangjin.software_project_server.dto.camera.ClientResponseDto
import com.rangjin.software_project_server.dto.camera.ClientsUpdateRequest
import com.rangjin.software_project_server.dto.camera.TablesUpdateRequest
import com.rangjin.software_project_server.repository.CameraRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.math.*

@Service
class CameraService(
    private val cameraRepository: CameraRepository,
) {

    @Transactional
    fun createCamera(): Long? {
        return cameraRepository.save(Camera()).id
    }

    @Transactional
    fun getCamera(id: Long): CameraResponseDto? {
        return cameraRepository.findByIdOrNull(id)?.let { CameraResponseDto(it) }
    }

    @Transactional
    fun saveTables(request: TablesUpdateRequest) {
        val camera = cameraRepository.findByIdOrNull(request.id)
        camera?.updateTables(request.tables)
    }

    @Transactional
    fun saveClients(request: ClientsUpdateRequest) {
        val camera = cameraRepository.findByIdOrNull(request.id)
        val tables = camera?.tables
        val clients = request.clients
        for (client in clients) {
            var distance = Double.MAX_VALUE
            var num: Int = -1
            var k: Double
            for ((n, table) in tables!!.withIndex()) {
                k = ((client.cltX1 + client.cltX2 / 2) - (table.tabX1 + table.tabX2 / 2)).toDouble().pow(2) +
                        ((client.cltY1 + client.cltY2 / 2) - (table.tabY1 + table.tabY2 / 2)).toDouble().pow(2)
                if (k < distance) {
                    distance = k
                    num = n
                }
            }
            client.sitTable = num
        }
        camera?.updateClients(clients)
    }

}

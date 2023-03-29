package com.rangjin.software_project_server.service

import com.rangjin.software_project_server.domain.Camera
import com.rangjin.software_project_server.dto.camera.ClientsUpdateRequest
import com.rangjin.software_project_server.dto.camera.TablesUpdateRequest
import com.rangjin.software_project_server.repository.CameraRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
            if (!client.sit) {
                client.sitTable = -1
            } else {
                var distance = 0.0
                var num: Int = -1
                var k: Double
                for ((n, table) in tables!!.withIndex()) {
                    k = sqrt(
                        ((client.cltX + client.cltHeight / 2).toDouble() - (table.tabX + table.tabHeight / 2).toDouble()).pow(
                            2
                        )
                                + ((client.cltY + client.cltWidth / 2).toDouble() - (table.tabY + table.tabWidth / 2).toDouble()).pow(
                            2
                        )
                    )
                    if (distance == 0.0 || k < distance) {
                        distance = k
                        num = n
                    }
                }
                client.sitTable = num
            }
        }
        camera?.updateClients(clients)
    }

}

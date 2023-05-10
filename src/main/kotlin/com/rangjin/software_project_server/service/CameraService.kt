package com.rangjin.software_project_server.service

import com.rangjin.software_project_server.domain.Camera
import com.rangjin.software_project_server.dto.camera.*
import com.rangjin.software_project_server.repository.CameraRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import kotlin.math.pow


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
        // TODO: null 일 때 error 처리
        return cameraRepository.findByIdOrNull(id)?.let { CameraResponseDto(it) }
    }

    @Transactional
    fun getImage(id: Long): String? {
        return cameraRepository.findByIdOrNull(id)?.image
    }

    @Transactional
    fun updateImage(id: Long, request: ImageRequestDto) {
        val camera = cameraRepository.findByIdOrNull(id)
        camera?.updateImage(request.image)

        val restTemplate = RestTemplate()
        val httpHeaders = org.springframework.http.HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON

        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        body.add("image", request.image)
        val requestMessage: HttpEntity<*> = HttpEntity(body, httpHeaders)
        val response: HttpEntity<String> = restTemplate.postForEntity<String>("http://127.0.0.1:5000/analyze", requestMessage, String::class.java)

        print(response)
    }

    @Transactional
    fun updateTables(id: Long, request: TablesRequestDto) {
        // TODO: null 일 때 error 처리
        val camera = cameraRepository.findByIdOrNull(id)
        camera?.updateTables(request.tables)
    }

    @Transactional
    fun updateClients(id: Long, request: ClientsRequestDto) {
        // TODO: null 일 때 error 처리
        val camera = cameraRepository.findByIdOrNull(id)
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

    @Transactional
    fun updateCamera(id: Long, request: CameraRequestDto) {
        updateTables(id, request.tables)
        updateClients(id, request.clients)
    }

}

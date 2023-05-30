package com.rangjin.software_project_server.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.rangjin.software_project_server.domain.Camera
import com.rangjin.software_project_server.dto.camera.*
import com.rangjin.software_project_server.exception.BaseException
import com.rangjin.software_project_server.exception.BaseResponseCode
import com.rangjin.software_project_server.repository.CameraRepository
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.lang.Integer.max
import java.lang.Math.min
import kotlin.math.pow


@PropertySource("application.properties")
@Service
class CameraService(
    private val cameraRepository: CameraRepository,
    private val environment: Environment,
) {

    @Transactional
    fun loginCamera(requestDto: LoginRequestDto): Long? {
        return if (cameraRepository.existsByName(requestDto.name)) {
            signIn(requestDto)
        } else {
            signUp(requestDto)
        }
    }

    @Transactional
    fun signIn(requestDto: LoginRequestDto): Long {
        val camera = cameraRepository.findByName(requestDto.name)
        if (camera.password == requestDto.password) {
            return camera.id!!
        } else {
            throw BaseException(BaseResponseCode.INVALID_PASSWORD)
        }
    }

    @Transactional
    fun signUp(requestDto: LoginRequestDto): Long {
        val camera = Camera(requestDto)
        return cameraRepository.save(camera).id!!
    }

    @Transactional
    fun getCamera(id: Long): CameraResponseDto? {
        return cameraRepository.findByIdOrNull(id)?.let { CameraResponseDto(it) } ?: throw BaseException(BaseResponseCode.NOT_FOUND)
    }

    @Transactional
    fun getAllCamera(): MutableList<SimpleCameraResponseDto> {
        val list: MutableList<SimpleCameraResponseDto> = mutableListOf()
        cameraRepository.findAll().forEach {
            camera -> list.add(SimpleCameraResponseDto(camera))
        }
        return list
    }

    @Transactional
    fun updateImage(id: Long, request: ImageRequestDto) {
        val camera = cameraRepository.findByIdOrNull(id) ?: throw BaseException(BaseResponseCode.NOT_FOUND)
        camera.updateImage(request.image)

        val restTemplate = RestTemplate()
        val httpHeaders = org.springframework.http.HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON

        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        body.add("image", request.image)
        val requestMessage: HttpEntity<*> = HttpEntity(body, httpHeaders)

        if (request.state == 1) {
            val response: HttpEntity<String> = restTemplate.postForEntity<String>(
                environment.getProperty("ml.url") + "/client", requestMessage, String::class.java)

            val objectMapper = ObjectMapper()
            updateClients(id, objectMapper.readValue(response.body, ClientsRequestDto::class.java))
        } else {
            val response: HttpEntity<String> = restTemplate.postForEntity<String>(
                environment.getProperty("ml.url") + "/table", requestMessage, String::class.java)

            val objectMapper = ObjectMapper()
            updateTables(id, objectMapper.readValue(response.body, TablesRequestDto::class.java))
        }
    }

    @Transactional
    fun updateTables(id: Long, request: TablesRequestDto) {
        val camera = cameraRepository.findByIdOrNull(id) ?: throw BaseException(BaseResponseCode.NOT_FOUND)
        camera.updateTables(request.tables)
        camera.updateClients(emptyList())
    }

    @Transactional
    fun updateClients(id: Long, request: ClientsRequestDto) {
        val camera = cameraRepository.findByIdOrNull(id) ?: throw BaseException(BaseResponseCode.NOT_FOUND)
        val tables = camera.tables
        val clients = request.clients
        for (client in clients) {
            var area = -1
            var num: Int = -1
            var k: Int
            for ((n, table) in tables.withIndex()) {
//                k = ((client.cltX1 + client.cltX2 / 2) - (table.tabX1 + table.tabX2 / 2)).toDouble().pow(2) +
//                        ((client.cltY1 + client.cltY2 / 2) - (table.tabY1 + table.tabY2 / 2)).toDouble().pow(2)
                k = intersectArea(client, table)
                if (k > area) {
                    area = k
                    num = n
                }
            }
            client.sitTable = num
        }
        camera.updateClients(clients)
    }

    fun intersectArea(client: Camera.Client, table: Camera.Table): Int {
        // case1 오른쪽으로 벗어나 있는 경우
        if (client.cltX2 < table.tabX1) return 0

        // case2 왼쪽으로 벗어나 있는 경우
        if (client.cltX1 > table.tabX2) return 0

        // case3 위쪽으로 벗어나 있는 경우
        if (client.cltY2 < table.tabY1) return 0

        // case4 아래쪽으로 벗어나 있는 경우
        if (client.cltY1 > table.tabY2) return 0

        val leftUpX = max(client.cltX1, table.tabX1)
        val leftUpY = max(client.cltY1, table.tabY1)
        val rightDownX = min(client.cltX2, table.tabX2)
        val rightDownY = min(client.cltY2, table.tabY2)

        val width = rightDownX - leftUpX
        val height =  rightDownY - leftUpY

        return width * height
    }

}

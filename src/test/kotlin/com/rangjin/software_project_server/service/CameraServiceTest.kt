package com.rangjin.software_project_server.service

import com.rangjin.software_project_server.domain.Camera
import com.rangjin.software_project_server.dto.camera.ClientsRequestDto
import com.rangjin.software_project_server.dto.camera.ClientsResponseDto
import com.rangjin.software_project_server.dto.camera.TablesRequestDto
import com.rangjin.software_project_server.dto.camera.TablesResponseDto
import com.rangjin.software_project_server.repository.CameraRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class CameraServiceTest @Autowired constructor(
    private val cameraService: CameraService,
    private val cameraRepository: CameraRepository,
) {

    @AfterEach
    fun clean() {
        cameraRepository.deleteAll()
    }

    @Test
    @DisplayName("카메라를 등록한다")
    @Transactional
    fun saveCameraTest() {
        // given & when
        val id = cameraService.createCamera()

        // then
        val results = cameraRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].id).isEqualTo(id)
    }

    @Test
    @DisplayName("카메라 정보를 가져온다")
    @Transactional
    fun getCameraTest() {
        // given
        val camera = Camera()
        camera.tables = listOf(
            Camera.Table(1, 1, 2, 2),
            Camera.Table(3, 3, 4, 4),
        )
        camera.clients = listOf(
            Camera.Client(1, 1, 2, 2, 0),
            Camera.Client(4, 4, 5, 5, 1),
            Camera.Client(5, 5, 6, 6, 1),
        )
        val id = cameraRepository.save(camera).id

        // when
        val results = cameraService.getCamera(id!!)

        // then
        assertThat(results).isNotNull
        assertThat(results!!.tables).hasSize(2)
        assertThat(results.tables[0]).isEqualTo(TablesResponseDto(1, 1, 2, 2))
        assertThat(results.tables[1]).isEqualTo(TablesResponseDto(3, 3, 4, 4))

        assertThat(results.clients).hasSize(3)
        assertThat(results.clients[0]).isEqualTo(ClientsResponseDto(1, 1, 2, 2, 0))
        assertThat(results.clients[1]).isEqualTo(ClientsResponseDto(4, 4, 5, 5, 1))
        assertThat(results.clients[2]).isEqualTo(ClientsResponseDto(5, 5, 6, 6, 1))
    }

    @Test
    @DisplayName("테이블을 업데이트한다")
    @Transactional
    fun updateTableTest() {
        // given
        val id = cameraRepository.save(Camera()).id
        val request = TablesRequestDto(listOf(
                Camera.Table(1, 1, 2, 2),
                Camera.Table(3, 3, 4, 4),
        ))

        // when
        cameraService.updateTables(id!!, request)

        // then
        val results = cameraRepository.findAll()
        assertThat(results[0].tables).hasSize(2)
        assertThat(results[0].tables[0]).isEqualTo(Camera.Table(1, 1, 2, 2))
        assertThat(results[0].tables[1]).isEqualTo(Camera.Table(3, 3, 4, 4))
    }

    @Test
    @DisplayName("사람을 업데이트한다")
    @Transactional
    fun updateClientsTest() {
        // given
        val camera = Camera()
        camera.tables = listOf(
            Camera.Table(1, 1, 2, 2),
            Camera.Table(3, 3, 4, 4),
        )
        val id = cameraRepository.save(camera).id

        val request = ClientsRequestDto(listOf(
            Camera.Client(1, 1, 2, 2, -1),
            Camera.Client(4, 4, 5, 5, -1),
            Camera.Client(5, 5, 6, 6, -1),
        ))

        // when
        cameraService.updateClients(id!!, request)

        // then
        val results = cameraRepository.findAll()
        assertThat(results[0].clients).hasSize(3)
        assertThat(results[0].clients[0]).isEqualTo(Camera.Client(1, 1, 2, 2, 0))
        assertThat(results[0].clients[1]).isEqualTo(Camera.Client(4, 4, 5, 5, 1))
        assertThat(results[0].clients[2]).isEqualTo(Camera.Client(5, 5, 6, 6, 1))
    }

}
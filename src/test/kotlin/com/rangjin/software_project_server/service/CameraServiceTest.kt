package com.rangjin.software_project_server.service

import com.rangjin.software_project_server.domain.Camera
import com.rangjin.software_project_server.dto.camera.TableUpdateRequest
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
    @DisplayName("테이블을 업데이트한다")
    @Transactional
    fun updateTableTest() {
        // given
        val id = cameraRepository.save(Camera()).id
        val request = TableUpdateRequest(id!!,
            listOf(
                Camera.Table(1, 1, 2, 2),
                Camera.Table(3, 3, 4, 4),
        ))

        // when
        cameraService.saveTables(request)

        // then
        val results = cameraRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].tables).hasSize(2)
        assertThat(results[0].tables[0].x).isEqualTo(1)
        assertThat(results[0].tables[0].y).isEqualTo(1)
        assertThat(results[0].tables[0].height).isEqualTo(2)
        assertThat(results[0].tables[0].width).isEqualTo(2)

        assertThat(results[0].tables[1].x).isEqualTo(3)
        assertThat(results[0].tables[1].y).isEqualTo(3)
        assertThat(results[0].tables[1].height).isEqualTo(4)
        assertThat(results[0].tables[1].width).isEqualTo(4)
    }

}
package com.rangjin.software_project_server.service

import com.rangjin.software_project_server.domain.Camera
import com.rangjin.software_project_server.dto.camera.*
import com.rangjin.software_project_server.repository.CameraRepository
import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecutor
import org.apache.commons.exec.PumpStreamHandler
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.ByteArrayOutputStream
import kotlin.math.*
import java.io.IOException

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
//        // Start subprocess
//        val processHandle = ProcessBuilder("python", "C:/Users/Sangjin/Desktop/Projects/Software-Project-Server/lib/main.py", "--id", id.toString()).start()
//        // Wait subprocess to terminate
//        val returnCode = processHandle.waitFor()
//        println(returnCode)
        val command = arrayOfNulls<String>(4)
        command[0] = "python"
        //command[1] = "\\workspace\\java-call-python\\src\\main\\resources\\test.py";
        //command[1] = "\\workspace\\java-call-python\\src\\main\\resources\\test.py";
        command[1] = "C:/Users/Sangjin/Desktop/Projects/Software-Project-Server/lib/main.py"
        command[2] = "10"
        command[3] = "10"
        try {
            execPython(command)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class, InterruptedException::class)
    fun execPython(command: Array<String?>) {
        val commandLine: CommandLine = CommandLine.parse(command[0])
        var i = 1
        val n = command.size
        while (i < n) {
            commandLine.addArgument(command[i])
            i++
        }
        val outputStream = ByteArrayOutputStream()
        val pumpStreamHandler = PumpStreamHandler(outputStream)
        val executor = DefaultExecutor()
        executor.streamHandler = pumpStreamHandler
        val result: Int = executor.execute(commandLine)
        println("result: $result")
        println("output: $outputStream")
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

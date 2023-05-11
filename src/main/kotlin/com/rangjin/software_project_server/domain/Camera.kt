package com.rangjin.software_project_server.domain

import com.rangjin.software_project_server.dto.camera.LoginRequestDto
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
class Camera (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @NotNull
    val username: String,

    @NotNull
    val password: String,

    @Column(columnDefinition="TEXT", length = 4096)
    var image: String,

    @ElementCollection
    @CollectionTable(name = "camera_tables")
    var tables: List<Table>,

    @ElementCollection
    @CollectionTable(name = "camera_clients")
    var clients: List<Client>,
){

    constructor(): this(null, "", "", "", emptyList(), emptyList())
    constructor(requestDto: LoginRequestDto) : this(null, requestDto.username, requestDto.password, "", emptyList(), emptyList())

    @Embeddable
    data class Table(
        var tabX1: Int,
        var tabY1: Int,
        var tabX2: Int,
        var tabY2: Int,
    ) {
        constructor() : this(0, 0, 0, 0)
    }

    @Embeddable
    data class Client(
        var cltX1: Int,
        var cltY1: Int,
        var cltX2: Int,
        var cltY2: Int,
        var sitTable: Int = -1,
    ) {
        constructor() : this(0, 0, 0, 0, -1)
    }

    fun updateTables(tables: List<Table>) {
        this.tables = tables
    }

    fun updateClients(client: List<Client>) {
        this.clients = client
    }

    fun updateImage(image: String) {
        this.image = image
    }

}
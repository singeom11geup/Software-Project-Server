package com.rangjin.software_project_server.domain

import jakarta.persistence.*

@Entity
class Camera (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ElementCollection
    @CollectionTable(name = "camera_tables")
    var tables: List<Table>,

    @ElementCollection
    @CollectionTable(name = "camera_clients")
    var clients: List<Client>,
){

    constructor(): this(null, emptyList(), emptyList())

    @Embeddable
    data class Table(
        var tabX1: Int,
        var tabY1: Int,
        var tabX2: Int,
        var tabY2: Int,
    )

    @Embeddable
    data class Client(
        var cltX1: Int,
        var cltY1: Int,
        var cltX2: Int,
        var cltY2: Int,
        var sitTable: Int = -1,
    )

    fun updateTables(tables: List<Table>) {
        this.tables = tables
    }

    fun updateClients(client: List<Client>) {
        this.clients = client
    }

}
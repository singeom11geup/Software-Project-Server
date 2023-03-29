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
        var tabX: Int,
        var tabY: Int,
        var tabHeight: Int,
        var tabWidth: Int,
    )

    @Embeddable
    data class Client(
        var cltX: Int,
        var cltY: Int,
        var cltHeight: Int,
        var cltWidth: Int,
        var sit: Boolean,
        var sitTable: Int,
    )

    fun updateTables(tables: List<Table>) {
        this.tables = tables
    }

    fun updateClients(client: List<Client>) {
        this.clients = client
    }

}
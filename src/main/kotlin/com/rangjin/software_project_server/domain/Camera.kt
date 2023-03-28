package com.rangjin.software_project_server.domain

import jakarta.persistence.*

@Entity
class Camera (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ElementCollection
    var tables: List<Table>,
){

    constructor(): this(null, emptyList())

    @Embeddable
    data class Table(
        var x: Int,
        var y: Int,
        var height: Int,
        var width: Int,
    )

    fun updateTables(tables: List<Table>) {
        this.tables = tables
    }

}
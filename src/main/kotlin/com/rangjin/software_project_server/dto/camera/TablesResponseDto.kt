package com.rangjin.software_project_server.dto.camera

import com.rangjin.software_project_server.domain.Camera

data class TablesResponseDto(
    var tabX1: Int?,
    var tabY1: Int?,
    var tabX2: Int?,
    var tabY2: Int?,
) {
    constructor(table: Camera.Table): this(table.tabX1, table.tabY1, table.tabX2, table.tabY2)
}
package com.rangjin.software_project_server.dto.camera

import com.rangjin.software_project_server.domain.Camera

data class ClientResponseDto(
    var cltX1: Int?,
    var cltY1: Int?,
    var cltX2: Int?,
    var cltY2: Int?,
    var sitTable: Int?,
) {
    constructor(client: Camera.Client?): this(client?.cltX1, client?.cltY1, client?.cltX2, client?.cltY2, client?.sitTable)
}
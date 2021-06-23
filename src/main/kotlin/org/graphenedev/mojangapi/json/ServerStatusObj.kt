package org.graphenedev.mojangapi.json

internal data class ServerStatusResponse (
    val version: ServerStatusVersion,
    val players: ServerStatusPlayers,
    val description: ServerStatusDescription
)

internal data class ServerStatusDescription (
    val text: String
)

internal data class ServerStatusPlayers (
    val max: Long,
    val online: Long,
    val sample: List<ServerStatusSample>
)

internal data class ServerStatusSample (
    val name: String,
    val id: String
)

internal data class ServerStatusVersion (
    val name: String,
    val protocol: Long
)

package de.crash.json

data class ServerStatusObj (
    val version: ServerStatusVersion,
    val players: ServerStatusPlayers,
    val description: ServerStatusDescription
)

data class ServerStatusDescription (
    val text: String
)

data class ServerStatusPlayers (
    val max: Long,
    val online: Long,
    val sample: List<ServerStatusSample>
)

data class ServerStatusSample (
    val name: String,
    val id: String
)

data class ServerStatusVersion (
    val name: String,
    val protocol: Long
)

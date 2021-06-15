package de.crash.mojangapi.json

internal data class ProfileResponse (
    val id: String,
    val name: String,
    val properties: List<ProfileProperty>,
    val legacy: Boolean?
)

data class ProfileProperty (
    val name: String,
    val value: String
)

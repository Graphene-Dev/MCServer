package de.crash.mojangapi.json

internal class UsernamesToUUIDsResponse(elements: Collection<UsernameToUUIDResponse>) : ArrayList<UsernameToUUIDResponse>(elements)

internal data class UsernameToUUIDResponse (
    val id: String,
    val name: String
)

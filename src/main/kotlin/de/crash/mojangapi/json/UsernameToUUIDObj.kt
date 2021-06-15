package de.crash.mojangapi.json

class UsernamesToUUIDsResponse(elements: Collection<UsernameToUUIDResponse>) : ArrayList<UsernameToUUIDResponse>(elements)

data class UsernameToUUIDResponse (
    val id: String,
    val name: String
)

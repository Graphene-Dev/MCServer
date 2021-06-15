package de.crash.mojangapi.json

internal class NameHistoryResponse(elements: Collection<NameHistoryElement>) : ArrayList<NameHistoryElement>(elements)

internal data class NameHistoryElement (
    val name: String,
    val changedToAt: Long? = null
)

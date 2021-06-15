package de.crash.mojangapi.json

internal class NameHistoryRespose(elements: Collection<NameHistoryElement>) : ArrayList<NameHistoryElement>(elements)

internal data class NameHistoryElement (
    val name: String,
    val changedToAt: Long? = null
)

package de.crash.netty

enum class ClientStatus {
    HANDSHAKE,
    STATUS,
    LOGIN,
    PLAY
}

fun getStateById(id: Int): ClientStatus = when(id){
    0 -> ClientStatus.HANDSHAKE
    1 -> ClientStatus.STATUS
    2 -> ClientStatus.LOGIN
    3 -> ClientStatus.PLAY
    else -> ClientStatus.HANDSHAKE
}
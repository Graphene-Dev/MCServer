package de.crash.netty.packets

enum class PacketType(val id: Int) {
    HANDSHAKE(0),
    RESPONSE(0),
    REQUEST(0),
    PING(1),
    PONG(1),
    LOGIN_START(0),
    LOGIN_SUCCESS(2),
    JOIN_GAME(26)
}
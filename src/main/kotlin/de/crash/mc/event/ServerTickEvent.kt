package de.crash.mc.event

class ServerTickEvent : MCEvent() {
    override fun fire() {
        super.fire()
        println("Server Tick")
    }
}
package de.crash.mc.event

class ServerTickEvent(val thisTickTime: Long = System.currentTimeMillis()) : MCEvent(), Cancelable {
    override fun fire() {
        super.fire()
    }
}
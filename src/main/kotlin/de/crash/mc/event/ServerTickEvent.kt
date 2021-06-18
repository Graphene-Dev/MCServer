package de.crash.mc.event

import de.crash.mc.Server

class ServerTickEvent(val thisTickTime: Long = System.currentTimeMillis()) : MCEvent(), Cancelable {
    override fun fire() {
        super.fire()
        //Server Tick stuff
        Server.lastTickFinished = true
    }
}
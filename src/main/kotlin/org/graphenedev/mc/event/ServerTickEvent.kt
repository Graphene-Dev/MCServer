package org.graphenedev.mc.event

import org.graphenedev.mc.Server

class ServerTickEvent(val thisTickTime: Long = System.currentTimeMillis()) : MCEvent(), Cancelable {
    override fun fire() {
        super.fire()
        //Server Tick stuff
        Server.lastTickFinished = true
    }
}
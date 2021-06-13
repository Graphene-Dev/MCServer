package de.crash.mc.entities.entity

import de.crash.mc.entities.EntityType
import de.crash.mc.entities.LivingEntity
import de.crash.netty.NettyClient

class Player(val nettyClient: NettyClient) : LivingEntity {
    constructor(nettyClient: NettyClient, name: String) : this(nettyClient) {

    }

    override val entityType: EntityType = EntityType.PLAYER
}
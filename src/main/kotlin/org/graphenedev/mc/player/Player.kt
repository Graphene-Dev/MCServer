package org.graphenedev.mc.player

import org.graphenedev.mc.entities.EntityType
import org.graphenedev.mc.entities.LivingEntity
import org.graphenedev.netty.NettyClient
import java.util.*

class Player internal constructor(val nettyClient: NettyClient, val name: String, val uuid: UUID) : LivingEntity {
    override var isCustomNameVisible: Boolean = false
    override val entityType: EntityType = EntityType.PLAYER
}
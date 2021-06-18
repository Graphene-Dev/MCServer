package de.crash.mc.player

import de.crash.mc.entities.EntityType
import de.crash.mc.entities.LivingEntity
import de.crash.netty.NettyClient
import java.util.*

class Player internal constructor(val nettyClient: NettyClient, val name: String, val uuid: UUID) : LivingEntity {
    override var isCustomNameVisible: Boolean = false
    override val entityType: EntityType = EntityType.PLAYER
}
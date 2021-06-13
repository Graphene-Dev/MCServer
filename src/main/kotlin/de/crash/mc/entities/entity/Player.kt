package de.crash.mc.entities.entity

import de.crash.mc.entities.EntityType
import de.crash.mc.entities.LivingEntity
import de.crash.netty.NettyClient
import java.util.*

class Player(val nettyClient: NettyClient, val name: String, val uuid: UUID) : LivingEntity {

    override val entityType: EntityType = EntityType.PLAYER
}
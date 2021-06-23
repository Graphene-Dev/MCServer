package org.graphenedev.mc.entities.entity

import org.graphenedev.mc.entities.EntityType
import org.graphenedev.mc.entities.LivingEntity

class Cow : LivingEntity {
    override val entityType: EntityType = EntityType.COW
    override var isCustomNameVisible: Boolean = false
}
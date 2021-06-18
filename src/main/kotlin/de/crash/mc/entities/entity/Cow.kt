package de.crash.mc.entities.entity

import de.crash.mc.entities.EntityType
import de.crash.mc.entities.LivingEntity

class Cow : LivingEntity {
    override val entityType: EntityType = EntityType.COW
    override var isCustomNameVisible: Boolean = false
}
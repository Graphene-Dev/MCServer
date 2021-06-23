package org.graphenedev.mc.event

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
abstract class MCEvent {
    var isCancelled: Boolean = false

    internal open fun fire() {
        eventListener[this::class]?.forEach {
            it.invoke(this)
            if(this is Cancelable && isCancelled) return
        }
    }

    companion object {
        val eventListener: HashMap<KClass<*>, MutableList<(event: MCEvent) -> Unit>> = hashMapOf()
        inline fun <reified T: MCEvent> on(noinline function: (event: T) -> Unit){
            val tClass = T::class
            eventListener[tClass] ?: eventListener.put(tClass, mutableListOf())
            eventListener[tClass]!!.add(function as (MCEvent) -> Unit)
        }
    }
}
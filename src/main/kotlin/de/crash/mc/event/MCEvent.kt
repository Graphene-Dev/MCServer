package de.crash.mc.event

@Suppress("UNCHECKED_CAST")
abstract class MCEvent {
    open fun fire() {
        actions.forEach {
            it.value.invoke(this)
        }
    }

    companion object {
        internal val actions: HashMap<MCEvent,(event: MCEvent) -> Unit> = hashMapOf()
        fun on(event: MCEvent, function: (event: MCEvent) -> Unit){
            actions[event] = function
        }
    }
}
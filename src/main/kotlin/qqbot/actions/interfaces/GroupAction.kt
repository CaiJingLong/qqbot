package qqbot.actions.interfaces

interface GroupAction {

    fun supportGroupIds(): List<Long>? = null

}

inline fun GroupAction.runGroup(id: Long, block: () -> Unit) {
    val supportGroupIds = supportGroupIds()
    if (supportGroupIds == null) {
        block()
    } else if (supportGroupIds.contains(id)) {
        block()
    }
}
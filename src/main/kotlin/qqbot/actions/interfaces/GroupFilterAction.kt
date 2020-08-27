package qqbot.actions.interfaces

import io.ktor.client.HttpClient
import net.mamoe.mirai.message.GroupMessageEvent


/**
 * 只在某些群内才有效的action
 */
interface GroupFilterAction : GroupAction {

    companion object Inner {
        val httpClient = HttpClient()
    }

    val httpClient
        get() = Inner.httpClient

    fun supportIds(): List<Long>? = null

    /**
     * 屏蔽指定的qq号(个人, 主要是为了机器人)
     */
    fun blockQQ(): List<Long>? = null

    suspend fun invoke(event: GroupMessageEvent) {
        checkQQ(event) {
            runGroup(event.group.id) {
                val supportIds = supportIds()
                if (supportIds == null) {
                    onInvoke(event)
                    return
                }
                if (supportIds.contains(event.group.id)) {
                    onInvoke(event)
                }
            }
        }
    }

    suspend fun onInvoke(event: GroupMessageEvent)
}

inline fun GroupFilterAction.checkQQ(event: GroupMessageEvent, block: () -> Unit) {
    val blockQQ = blockQQ()
    if (blockQQ == null) {
        block()
    } else if (!blockQQ.contains(event.sender.id)) {
        block()
    }
}

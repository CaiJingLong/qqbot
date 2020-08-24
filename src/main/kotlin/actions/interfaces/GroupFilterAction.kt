package actions.interfaces

import io.ktor.client.HttpClient
import net.mamoe.mirai.message.GroupMessageEvent

/**
 * 只在某些群内才有效的action
 */
interface GroupFilterAction {

    companion object Inner {
        val httpClient = HttpClient()
    }

    val httpClient
        get() = Inner.httpClient

    fun ids(): List<Long>

    suspend fun invoke(event: GroupMessageEvent) {
        if (ids().contains(event.group.id)) {
            onInvoke(event)
        }
    }

    suspend fun onInvoke(event: GroupMessageEvent)
}
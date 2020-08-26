package actions.interfaces

import io.ktor.client.HttpClient
import net.mamoe.mirai.message.GroupMessageEvent

/**
 * 表示群组action
 */
interface GroupAction {


    companion object Inner {
        val httpClient = HttpClient()
    }

    val httpClient
        get() = Inner.httpClient

    fun supportGroupIds(): List<Long>? = null

    suspend fun invoke(event: GroupMessageEvent, params: String) {
        val supportGroupIds = supportGroupIds()
        if (supportGroupIds == null) {
            onInvoke(event, params)
        } else {
            if (supportGroupIds.contains(event.group.id)) {
                onInvoke(event, params)
            }
        }
    }

    /**
     * 触发时的回调, [event]是触发的回调, [params]是参数
     */
    suspend fun onInvoke(event: GroupMessageEvent, params: String)

}
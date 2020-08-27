package qqbot.actions.group.at

import qqbot.actions.interfaces.GroupFilterAction
import io.ktor.client.request.get
import net.mamoe.mirai.message.GroupMessageEvent
import qqbot.constants.Constants
import qqbot.utils.Throttle
import kotlin.random.Random

/**
 * 自动回复
 */
object AutoReplyAction : GroupFilterAction {
    override fun supportGroupIds(): List<Long>? {
        return Constants.onlyBig
    }

    private val throttle = Throttle(10000)

    private val types = listOf(
        "soup",
        "zha",
        "fart",
        "social"
    )

    override suspend fun onInvoke(event: GroupMessageEvent) {
        throttle.run {
            val i = Random.nextInt(1000) % types.count()
            val type = types[i]
            val body = httpClient.get<String>("https://api.uixsj.cn/hitokoto/get?type=$type")
            event.quoteReply(body)
        }

    }
}
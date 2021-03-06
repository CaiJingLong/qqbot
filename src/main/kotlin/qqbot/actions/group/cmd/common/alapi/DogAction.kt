package qqbot.actions.group.cmd.common.alapi

import qqbot.entity.alapi.Dog
import net.mamoe.mirai.message.GroupMessageEvent
import qqbot.utils.OKHttp
import qqbot.utils.moshi

object DogAction : ALCmdAction {

    override fun showHelperText(): Boolean {
        return false
    }

    override val prefix: String
        get() = "/dog"

    override fun prefixAlias(): List<String> {
        return listOf(
            "/舔狗",
            "/舔狗日记",
        )
    }

    override fun helperText(): String {
        return "/dog 舔狗日记"
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        val str = OKHttp.getString("https://v1.alapi.cn/api/dog")
        val adapter = moshi.adapter(Dog::class.java)
        val entity = adapter.fromJson(str)
        entity?.data?.content?.let {
            event.quoteReply(it)
        }
    }
}
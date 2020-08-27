package qqbot.actions.group.cmd.common.alapi

import qqbot.entity.alapi.AlQinghua
import net.mamoe.mirai.message.GroupMessageEvent
import qqbot.utils.OKHttp
import qqbot.utils.moshi

object QinghuaAction : ALCmdAction {

    override fun showHelperText(): Boolean {
        return false
    }

    override val prefix: String
        get() = "/qinghua"

    override fun prefixAlias(): List<String> {
        return listOf(
            "/情话",
            "/土味情话",
        )
    }

    override fun helperText(): String {
        return "/qinghua 土味情话"
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        val str = OKHttp.getString("https://v1.alapi.cn/api/qinghua")
        val adapter = moshi.adapter(AlQinghua::class.java)
        val entity = adapter.fromJson(str)
        entity?.data?.content?.let {
            event.quoteReply(it)
        }
    }
}
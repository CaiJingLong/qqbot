package qqbot.actions.group.cmd.common.alapi

import qqbot.entity.alapi.Soul
import net.mamoe.mirai.message.GroupMessageEvent
import qqbot.utils.OKHttp
import qqbot.utils.moshi

object SoulAction : ALCmdAction {

    override fun showHelperText(): Boolean {
        return false
    }

    override val prefix: String
        get() = "/soul"

    override fun prefixAlias(): List<String> {
        return listOf(
            "/心灵毒鸡汤",
            "/毒鸡汤",
        )
    }

    override fun helperText(): String {
        return "/soul 心灵毒鸡汤"
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        val str = OKHttp.getString("https://v1.alapi.cn/api/soul")
        val adapter = moshi.adapter(Soul::class.java)
        val entity = adapter.fromJson(str)
        entity?.data?.title?.let {
            event.quoteReply(it)
        }
    }
}
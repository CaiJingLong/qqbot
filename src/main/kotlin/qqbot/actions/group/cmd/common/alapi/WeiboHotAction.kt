package qqbot.actions.group.cmd.common.alapi

import qqbot.entity.alapi.WeiboHotWord
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.buildMessageChain
import qqbot.utils.OKHttp
import qqbot.utils.toBean

object WeiboHotAction : ALCmdAction {

    override val prefix: String
        get() = "/weiboHot"

    override fun helperText(): String {
        return "/weiboHot 微博热词"
    }

    override fun prefixAlias(): List<String> {
        return listOf(
            "/微博热词"
        )
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        val url = OKHttp.getString("https://v1.alapi.cn/api/new/wbtop")
        val word = url.toBean<WeiboHotWord>() ?: return
        val msg = word.data.map {
            it.hot_word
        }.joinToString("\n")
        event.reply(buildMessageChain {
            add("微博热词:\n")
            add(msg)
        })
    }
}
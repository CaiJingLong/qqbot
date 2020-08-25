package actions.group.cmd.common.alapi

import actions.interfaces.CmdAction
import entity.alapi.AlQinghua
import entity.alapi.Dog
import entity.alapi.WeiboHotWord
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.message.sendAsImageTo
import net.mamoe.mirai.message.uploadAsImage
import okhttp3.HttpUrl
import okhttp3.Request
import utils.OKHttp
import utils.moshi
import utils.toBean
import java.net.URL

object WeiboHotAction : CmdAction {

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

    override suspend fun invoke(event: GroupMessageEvent, params: String) {
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
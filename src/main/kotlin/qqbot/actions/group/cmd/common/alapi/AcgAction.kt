package qqbot.actions.group.cmd.common.alapi

import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.sendAsImageTo
import qqbot.utils.OKHttp
import java.net.URL

object AcgAction : ALCmdAction {

    override fun showHelperText(): Boolean {
        return false
    }

    override val prefix: String
        get() = "/acg"

    override fun helperText(): String {
        return "/acg 来张acg图片"
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        val url = OKHttp.getLocationUrl("https://v1.alapi.cn/api/acg")
//        val targetUrl = "$url!/both/500x500"

        println("target url = $url")

        URL(url).openStream().use {
            it.sendAsImageTo(event.group)
        }
    }
}
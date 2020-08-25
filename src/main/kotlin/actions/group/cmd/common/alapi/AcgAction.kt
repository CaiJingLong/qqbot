package actions.group.cmd.common.alapi

import actions.interfaces.CmdAction
import entity.alapi.AlQinghua
import entity.alapi.Dog
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.message.sendAsImageTo
import net.mamoe.mirai.message.uploadAsImage
import okhttp3.HttpUrl
import okhttp3.Request
import utils.OKHttp
import utils.moshi
import java.net.URL

object AcgAction : CmdAction {

    override fun showHelperText(): Boolean {
        return false
    }

    override val prefix: String
        get() = "/acg"

    override fun helperText(): String {
        return "/acg 来张acg图片"
    }

    override suspend fun invoke(event: GroupMessageEvent, params: String) {
        val url = OKHttp.getLocationUrl("https://v1.alapi.cn/api/acg")
//        val targetUrl = "$url!/both/500x500"

        println("target url = $url")

        URL(url).openStream().use {
            it.sendAsImageTo(event.group)
        }
    }
}
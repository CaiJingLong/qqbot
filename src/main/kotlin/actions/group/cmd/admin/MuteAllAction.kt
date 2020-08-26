package actions.group.cmd.admin

import entity.Hello
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.sendAsImageTo
import net.mamoe.mirai.message.upload
import net.mamoe.mirai.message.uploadAsImage
import okhttp3.HttpUrl
import utils.OKHttp
import utils.moshi
import utils.toBean
import java.io.File
import java.net.URL
import javax.imageio.ImageIO
import kotlin.random.Random

/**
 * 开关灯
 */
object MuteAllAction : AdminCmdAction {
    override val prefix: String
        get() = "/muteAll"

    override fun prefixAlias(): List<String> {
        return listOf(
            "/开灯",
            "/关灯",
        )
    }

    override fun helperText(): String {
        return "$prefix: <y | n>, y对应关灯, n对应开灯, 只能由管理员发起"
    }


    override suspend fun onAdminInvoke(event: GroupMessageEvent, params: String) {
        when (event.message.content.trim().toLowerCase()) {
            "/开灯" -> {
                event.group.settings.isMuteAll = false
                event.reply(openMessage(event))
            }
            "/关灯" -> {
                event.group.settings.isMuteAll = true
                event.reply(closeMessage(event))
            }
        }

        when (params.trim().toLowerCase()) {
            "y" -> {
                event.group.settings.isMuteAll = true
                event.reply(closeMessage(event))
            }
            "n" -> {
                event.group.settings.isMuteAll = false
                event.reply(openMessage(event))
            }
        }
    }

    private suspend fun openMessage(groupMessageEvent: GroupMessageEvent): Message {
        val v = Random.nextInt(100000) % 639 + 1

        val url = HttpUrl.Builder()
            .scheme("http")
            .host("v2.api.haodanku.com")
            .addPathSegments("/get_salutation_data/apikey/candies")
            .addQueryParameter("category", "1")
            .addQueryParameter("back", "1")
            .addQueryParameter("min_id", v.toString())
            .build()
            .toString()

        val text = OKHttp.getString(url)
        val hello = text.toBean<Hello>()
        val data = hello?.data?.first() ?: return PlainText("早上好")

        return buildMessageChain {
            URL(data.imgsrc).openStream()?.use {
                it.uploadAsImage(groupMessageEvent.group)
            }?.apply {
                add(this.asMessageChain())
            }
            add("\n")
            add(PlainText(data.content))
        }
    }

    private suspend fun closeMessage(groupMessageEvent: GroupMessageEvent): Message {
        val v = Random.nextInt(100000) % 607 + 1

        val url = HttpUrl.Builder()
            .scheme("http")
            .host("v2.api.haodanku.com")
            .addPathSegments("/get_salutation_data/apikey/candies")
            .addQueryParameter("category", "3")
            .addQueryParameter("back", "1")
            .addQueryParameter("min_id", v.toString())
            .build()
            .toString()

        val text = OKHttp.getString(url)
        val hello = text.toBean<Hello>()
        val data = hello?.data?.first() ?: return PlainText("晚上好")

        return buildMessageChain {
            URL(data.imgsrc).openStream()?.use {
                it.uploadAsImage(groupMessageEvent.group)
            }?.apply {
                add(this.asMessageChain())
            }
            add("\n")
            add(PlainText(data.content))
        }
    }

}
package qqbot.actions.group.cmd.common

import qqbot.actions.interfaces.CmdAction
import qqbot.entity.Girl
import io.ktor.client.request.*
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.uploadAsImage
import qqbot.utils.moshi
import java.io.File

object GirlAction : CmdAction {
    override val prefix: String
        get() = "/小姐姐"

    override fun helperText(): String {
        return "/小姐姐 随机发一个小姐姐照片, 图片来源: Gank.io(https://gank.io/api/v2/random/category/Girl/type/Girl/count/1)"
    }

    override fun showHelperText(): Boolean {
        return false
    }

    private val allowList = arrayOf<Long>(
        262990989,
        410496936,
    )

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        if (!allowList.contains(event.sender.id)) {
            return
        }

        val url = try {
            val response = httpClient.get<String>("https://gank.io/api/v2/random/category/Girl/type/Girl/count/1")

            val adapter = moshi.adapter(Girl::class.java)
            val girl = adapter.fromJson(response)
            girl?.data?.firstOrNull()?.url
                ?: return
        } catch (e: Exception) {
            return
        }

        val file = File("/tmp/${System.currentTimeMillis()}")

        try {
            val byteArray = httpClient.get<ByteArray>(url)
            file.writeBytes(byteArray)

            var count = 0;
            while (count < 10) {
                try {
                    val image = file.uploadAsImage(event.group)
                    event.reply(image)
                    break
                } catch (e: Exception) {
                    count++
                }
            }
        } finally {
            file.delete()
        }

    }
}
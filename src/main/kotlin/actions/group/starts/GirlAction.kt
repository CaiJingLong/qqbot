package actions.group.starts

import actions.interfaces.CmdAction
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.sendAsImageTo
import net.mamoe.mirai.message.uploadAsImage
import java.io.File

object GirlAction : CmdAction {
    override val prefix: String
        get() = "/小姐姐"

    override fun helperText(): String {
        return "/小姐姐 随机发一个小姐姐照片, 图片来源: Gank.io(https://gank.io/api/v2/random/category/Girl/type/Girl/count/1)"
    }

    override suspend fun invoke(event: GroupMessageEvent, params: String) {
        val url = try {
            val response = httpClient.get<String>("https://gank.io/api/v2/random/category/Girl/type/Girl/count/1")
            Json.parseJson(response).jsonObject["data"]?.jsonArray?.get(0)?.jsonObject?.get("url")?.primitive?.content
                ?: return
        } catch (e: Exception) {
            return
        }

        if (event.sender.id != 262990989L) {
            return
        }

        val file = File("/tmp/${System.currentTimeMillis()}")

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
    }
}
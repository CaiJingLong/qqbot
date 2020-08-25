package actions.group.cmd.common.alapi

import actions.interfaces.CmdAction
import entity.alapi.AlQinghua
import net.mamoe.mirai.message.GroupMessageEvent
import utils.OKHttp
import utils.moshi

object QinghuaAction : CmdAction {

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

    override suspend fun invoke(event: GroupMessageEvent, params: String) {
        val str = OKHttp.getString("https://v1.alapi.cn/api/qinghua")
        val adapter = moshi.adapter(AlQinghua::class.java)
        val entity = adapter.fromJson(str)
        entity?.data?.content?.let {
            event.quoteReply(it)
        }
    }
}
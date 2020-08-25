package actions.group.cmd.common.alapi

import actions.interfaces.CmdAction
import entity.alapi.AlQinghua
import entity.alapi.Soul
import net.mamoe.mirai.message.GroupMessageEvent
import utils.OKHttp
import utils.moshi

object SoulAction : CmdAction {

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

    override suspend fun invoke(event: GroupMessageEvent, params: String) {
        val str = OKHttp.getString("https://v1.alapi.cn/api/soul")
        val adapter = moshi.adapter(Soul::class.java)
        val entity = adapter.fromJson(str)
        entity?.data?.title?.let {
            event.quoteReply(it)
        }
    }
}
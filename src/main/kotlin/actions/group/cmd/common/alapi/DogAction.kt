package actions.group.cmd.common.alapi

import actions.interfaces.CmdAction
import entity.alapi.AlQinghua
import entity.alapi.Dog
import net.mamoe.mirai.message.GroupMessageEvent
import utils.OKHttp
import utils.moshi

object DogAction : CmdAction {

    override val prefix: String
        get() = "/dog"

    override fun prefixAlias(): List<String> {
        return listOf(
            "/舔狗",
            "/舔狗日记",
        )
    }

    override fun helperText(): String {
        return "/dog 舔狗日记"
    }

    override suspend fun invoke(event: GroupMessageEvent, params: String) {
        val str = OKHttp.getString("https://v1.alapi.cn/api/dog")
        val adapter = moshi.adapter(Dog::class.java)
        val entity = adapter.fromJson(str)
        entity?.data?.content?.let {
            event.quoteReply(it)
        }
    }
}
package actions.group.admin

import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.buildMessageChain

object UnMuteAction : AdminCmdAction {
    override val prefix: String
        get() = "/unmute"

    override fun helperText(): String {
        return """/unmute <QQ号> """
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        val qq = params.toLongOrNull()

        if (qq == null) {
            event.quoteReply("qq号不能为空")
            return
        }

        val target = event.group.members[qq]

        target.unmute()
        event.quoteReply(buildMessageChain {
            add("${target.nick}(${target.id}) 取消禁言完成")
        })
    }
}
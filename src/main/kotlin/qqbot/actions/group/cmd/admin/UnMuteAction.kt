package qqbot.actions.group.cmd.admin

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

    override fun prefixAlias(): List<String> {
        return listOf(
            "/解禁",
            "/放出来",
            "/解",
            "/解封",
        )
    }

    override suspend fun onAdminInvoke(event: GroupMessageEvent, params: String) {
        val qq = params.toLongOrNull()

        if (qq == null) {
            useAt(event)
            return
        }

        val target = event.group.members[qq]

        target.unmute()
        event.quoteReply(buildMessageChain {
            add("${target.nick}(${target.id}) 取消禁言完成")
        })
    }


    private suspend fun useAt(event: GroupMessageEvent) {
        // 尝试使用at

        // 筛出要禁言的名单
        val members = event.message
            .filterIsInstance<At>() // at类型
            .filter {
                !event.group[it.target].isOperator() //不是管理员
            }.toList()

        if (members.isEmpty()) {
            event.quoteReply("无法确定要解禁何人")
            return
        }

        val text = members.joinToString {
            "${it.display}(${it.target})"
        }

        for (member in members) {
            event.group.members[member.target].unmute()
        }

        event.quoteReply(buildMessageChain {
            add(text)
            add("已被解禁")
        })

        return
    }
}
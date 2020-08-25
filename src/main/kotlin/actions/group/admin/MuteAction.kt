package actions.group.admin

import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.message.data.content

object MuteAction : AdminCmdAction {
    override val prefix: String
        get() = "/mute"

    override fun helperText(): String {
        return """/mute <QQ号> 时长(秒)"""
    }

    override suspend fun invoke(event: GroupMessageEvent, params: String) {
        if (event.message.content.startsWith(MuteAllAction.prefix)) {
            return
        }
        super.invoke(event, params)
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        val paramsList = params.split(" ")
        val second: Int
        val qq = paramsList[0].toLongOrNull()

        if (qq == null) {
            useAt(event)
            return
        }

        if (paramsList.size < 2) {
            event.quoteReply("未填写时长参数, 默认10分钟")
            second = 60 * 10
        } else {
            val s = paramsList[1].toIntOrNull()
            if (s == null) {
                event.quoteReply("未填写时长参数, 默认10分钟")
                second = 60 * 10
            } else {
                second = s
            }
        }

        val target = event.group.members.get(qq)
        if (target.isOperator()) {
            val msg = buildMessageChain {
                add("你想要禁言的")
                add(At(target))
                add("是管理员, 无法禁言")
            }
            event.quoteReply(msg)
            return
        }

        target.mute(second)
        event.quoteReply(buildMessageChain {
            add("${target.nick}(${target.id}) 禁言完成")
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
            event.quoteReply("不知道要禁言谁")
            return
        }

        // 找到时间消息体
        val second =
            try {
                event.message.toMutableList()
                    .filterIsInstance<PlainText>()
                    .first {
                        it.content.toIntOrNull() != null
                    }.content.toIntOrNull() ?: 600
            } catch (e: Exception) {
                60 * 10
            }

        val text = members.joinToString {
            it.display
        }

        for (member in members) {
            event.group.members[member.target].mute(second)
        }

        event.quoteReply(buildMessageChain {
            add(text)
            add("已被禁言")
        })

        return
    }
}
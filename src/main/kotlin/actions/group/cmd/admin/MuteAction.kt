package actions.group.cmd.admin

import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.message.data.content

object MuteAction : AdminCmdAction {
    override val prefix: String
        get() = "/mute"

    override fun prefixAlias(): List<String> {
        return listOf(
            "/小黑屋",
            "/闭嘴",
            "/禁言",
        )
    }

    override fun helperText(): String {
        return """/mute <QQ号> 时长, 单位:分钟"""
    }

    override suspend fun invoke(event: GroupMessageEvent, params: String) {
        if (event.message.content.startsWith(MuteAllAction.prefix)) {
            return
        }
        super.invoke(event, params)
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        val paramsList = params.split(" ")
        val minutes: Int
        val qq = paramsList[0].toLongOrNull()

        if (qq == null) {
            useAt(event)
            return
        }

        if (paramsList.size < 2) {
            event.quoteReply("未填写时长参数, 默认5分钟")
            minutes = 5
        } else {
            val s = paramsList[1].toIntOrNull()
            minutes = if (s == null) {
                event.quoteReply("未填写时长参数, 默认5分钟")
                5
            } else {
                s
            }
        }

        val target = event.group.members[qq]
        if (target.isOperator()) {
            val msg = buildMessageChain {
                add("你想要禁言的")
                add(At(target))
                add("是管理员, 无法禁言")
            }
            event.quoteReply(msg)
            return
        }

        target.mute(minutes * 60)
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
        val minutes =
            try {
                event.message.toMutableList()
                    .filterIsInstance<PlainText>()
                    .toMutableList()
                    .apply {
                        removeAt(0)
                    }
                    .firstOrNull()?.content?.trim()?.toIntOrNull()
                    ?: 5
            } catch (e: Exception) {
                5
            }

        println("minutes = $minutes")

        val text = members.joinToString {
            "${it.display}(${it.target})"
        }

        for (member in members) {
            event.group.members[member.target].mute(minutes * 60)
        }

        event.quoteReply(buildMessageChain {
            add(text)
            add("已被禁言")
        })

        return
    }
}
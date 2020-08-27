package qqbot.actions.group.cmd.admin

import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.At

/**
 * 踢人
 */
object KickAction : AdminCmdAction {
    override suspend fun onAdminInvoke(event: GroupMessageEvent, params: String) {
        val group = event.bot.getGroup(event.group.id)

        var targets = event.message
            .filterIsInstance<At>()
            .mapNotNull {
                group.getOrNull(it.target)
            }
            .filter {
                !it.isOperator()
            }

        if (targets.isEmpty()) { // 空的情况下, 继续检查 params 参数
            targets = params.split(" ")
                .mapNotNull {
                    it.trim().toLongOrNull()
                }
                .mapNotNull {
                    event.group.getOrNull(it)
                }
        }

        if (targets.isEmpty()) {
            event.quoteReply("可踢的人是空的")
            return
        }

        val qqText = targets.joinToString {
            "${it.nick}(${it.id})"
        }

        println("will send text = $qqText")

        for (it in targets) {
            it.kick()
        }

        event.quoteReply("$qqText 已被移除")
    }

    override fun prefixAlias(): List<String> {
        return listOf(
            "/踢",
            "/踢出去",
            "/踢人",
            "/remove",
            "/rm",
        )
    }

    override val prefix: String
        get() = "/kick"

    override fun helperText(): String {
        return "/kick <QQ号>"
    }

}
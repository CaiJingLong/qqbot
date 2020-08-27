package qqbot.actions.group.cmd.admin

import net.mamoe.mirai.message.GroupMessageEvent

object DenyAction : AdminCmdAction {

    override val prefix: String
        get() = "/deny"

    override fun helperText(): String {
        return """/deny 拒绝名单功能
            |/deny add @xxx 踢出群疗, 并在入群后会自动踢出
            |/deny add QQ 踢出群疗, 并在入群后会自动踢出
            |/deny remove QQ号 将某人移除出拒绝名单
            |/deny list
        """.trimMargin().trim()
    }

    override suspend fun onAdminInvoke(event: GroupMessageEvent, params: String) {

    }
}
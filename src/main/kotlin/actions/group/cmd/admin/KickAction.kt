package actions.group.cmd.admin

import net.mamoe.mirai.message.GroupMessageEvent

object KickAction : AdminCmdAction {
    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {

    }

    override val prefix: String
        get() = "/kick"

    override fun helperText(): String {
        return "/kick <QQ号>"
    }

}
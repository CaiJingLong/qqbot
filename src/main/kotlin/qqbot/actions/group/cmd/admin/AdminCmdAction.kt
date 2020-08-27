package qqbot.actions.group.cmd.admin

import qqbot.actions.interfaces.CmdAction
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.message.GroupMessageEvent

interface AdminCmdAction : CmdAction {

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        if (!event.sender.isOperator()) {
            event.quoteReply("你不是管理员或群主")
            return
        }
        if (!event.group.botPermission.isOperator()) {
            event.reply("机器人没管理权限")
            return
        }
        onAdminInvoke(event, params)
    }

    suspend fun onAdminInvoke(event: GroupMessageEvent, params: String)
}
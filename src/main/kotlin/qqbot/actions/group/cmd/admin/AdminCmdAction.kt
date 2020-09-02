package qqbot.actions.group.cmd.admin

import qqbot.actions.interfaces.CmdAction
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.message.GroupMessageEvent

interface AdminCmdAction : CmdAction {

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        if (!event.group.botPermission.isOperator()) {
            return
        }
        if (!event.sender.isOperator()) {
            onNormalUserInvoke(event, params)
            return
        }
        onAdminInvoke(event, params)
    }

    suspend fun onNormalUserInvoke(event: GroupMessageEvent, params: String) {}

    suspend fun onAdminInvoke(event: GroupMessageEvent, params: String)


}
package qqbot.actions.group.cmd.admin

import qqbot.actions.interfaces.CmdAction
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.message.GroupMessageEvent
import qqbot.constants.Constants

interface AdminCmdAction : CmdAction {

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        if (Constants.botQQ.contains(event.sender.id)) { // 如果是机器人发出,则忽略
            return
        }
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
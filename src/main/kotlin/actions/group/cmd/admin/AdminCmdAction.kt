package actions.group.cmd.admin

import actions.interfaces.CmdAction
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.message.GroupMessageEvent

interface AdminCmdAction : CmdAction {

    override suspend fun invoke(event: GroupMessageEvent, params: String) {
        if (!event.sender.isOperator()) {
            event.quoteReply("你不是管理员或群主")
            return
        }
        if (!event.group.botPermission.isOperator()) {
            event.reply("机器人没管理权限")
            return
        }
        onInvoke(event, params)
    }

    suspend fun onInvoke(event: GroupMessageEvent, params: String)
}
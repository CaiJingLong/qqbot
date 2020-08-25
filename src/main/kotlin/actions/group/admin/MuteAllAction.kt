package actions.group.admin

import actions.interfaces.CmdAction
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.message.GroupMessageEvent

/**
 * 开关灯
 */
object MuteAllAction : CmdAction {
    override val prefix: String
        get() = "/muteAll"

    override fun helperText(): String {
        return "$prefix: <y|n>, y对应关灯, n对应开灯"
    }

    override suspend fun invoke(event: GroupMessageEvent, params: String) {
        if (!event.sender.isOperator()) {
            event.quoteReply("你没有权限")
            return
        }
        if (!event.group.botPermission.isOperator()) {
            event.reply("机器人没权限")
            return
        }
        when (params.trim().toLowerCase()) {
            "y" -> {
                event.group.settings.isMuteAll = true
                event.reply("已关灯")
            }
            "n" -> {
                event.group.settings.isMuteAll = false
                event.reply("已开灯")
            }
            else -> {
                event.reply("不支持这个命令")
            }
        }
    }

}
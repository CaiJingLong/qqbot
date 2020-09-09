package qqbot.actions.group

import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.message.GroupMessageEvent
import qqbot.actions.interfaces.CmdAction

object HelpAction : CmdAction {

    override val prefix = "/h"

    val actions = ArrayList<CmdAction>()

    fun registerAction(action: CmdAction) {
        actions.add(action)
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        if (event.sender.isOperator()) {
            event.quoteReply(helperText())
        }
    }


    override fun helperText(): String {
        val sb = StringBuilder()

        sb.appendLine("以下为机器人使用帮助:")
        sb.appendLine("/h 显示本帮助")
        for (action in actions) {
            if (!action.showHelperText()) {
                continue
            }
            if (action != this) {
                sb.appendLine(action.helperText())
                if (action.prefixAlias().isNotEmpty()) {
                    sb.appendLine("   别名: ${action.prefixAlias().joinToString(",")}")
                }
                sb.appendLine()
            }
        }

        return sb.toString().trim()
    }
}

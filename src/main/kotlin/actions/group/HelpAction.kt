package actions.group

import actions.interfaces.CmdAction
import net.mamoe.mirai.message.GroupMessageEvent

object HelpAction : CmdAction {

    override val prefix = "/help"

    val actions = ArrayList<CmdAction>()

    fun registerAction(action: CmdAction) {
        actions.add(action)
    }

    override suspend fun invoke(event: GroupMessageEvent, params: String) {
        event.reply(helperText())
    }

    override fun helperText(): String {
        val sb = StringBuilder()

        sb.appendLine("以下为机器人使用帮助:")
        sb.appendLine("/help 显示本帮助")
        for (action in actions) {
            if (action != this) {
                sb.appendLine(action.helperText())
            }
        }

        return sb.toString().trim()
    }
}

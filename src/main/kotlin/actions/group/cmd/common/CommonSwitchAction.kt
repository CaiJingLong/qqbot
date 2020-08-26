package actions.group.cmd.common

import actions.interfaces.CmdAction
import net.mamoe.mirai.message.GroupMessageEvent

object CommonSwitchAction : CmdAction {
    override val prefix: String
        get() = "/小功能开关"

    override fun prefixAlias(): List<String> {
        return listOf(
            "/开启",
            "/关闭"
        )
    }

    override fun helperText(): String {
        return """/开启 [其他命令]
            |/关闭 [其他命令]
        """.trimMargin()
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {

    }


}
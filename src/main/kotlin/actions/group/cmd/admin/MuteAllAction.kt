package actions.group.cmd.admin

import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.content

/**
 * 开关灯
 */
object MuteAllAction : AdminCmdAction {
    override val prefix: String
        get() = "/muteAll"

    override fun prefixAlias(): List<String> {
        return listOf(
            "/开灯",
            "/关灯",
        )
    }

    override fun helperText(): String {
        return "$prefix: <y | n>, y对应关灯, n对应开灯, 只能由管理员发起"
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        when (event.message.content.trim().toLowerCase()) {
            "/开灯" -> {
                event.group.settings.isMuteAll = false
                event.reply("已开灯")
            }
            "/关灯" -> {
                event.group.settings.isMuteAll = true
                event.reply("已关灯")
            }
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
        }
    }

}
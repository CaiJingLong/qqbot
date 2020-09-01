package qqbot.actions.group.cmd.common

import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.sendTo
import qqbot.actions.interfaces.CmdAction

/**
 * 根据图片id拿图片
 */
object ShowImageAction : CmdAction {

    override val prefix: String
        get() = "/showImage"

    override fun helperText(): String {
        return "/showImage 图片id"
    }

    override fun showHelperText(): Boolean {
        return false
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        if (event.sender.id != 262990989L) {
            return
        }
        try {
            Image("{${params}}.mirai").sendTo(event.group)
        } catch (e: Exception) {
        }
    }

}
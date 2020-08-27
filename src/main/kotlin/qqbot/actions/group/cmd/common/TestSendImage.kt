package qqbot.actions.group.cmd.common

import net.mamoe.mirai.message.GroupMessageEvent
import qqbot.actions.interfaces.CmdAction
import java.io.File

object TestSendImage : CmdAction {
    override val prefix: String
        get() = "/sendTest"

    override fun helperText(): String {
        return "/sendTest 发测试图片"
    }

    override suspend fun onInvoke(event: GroupMessageEvent, params: String) {
        event.sendImage(File("/Users/jinglongcai/Desktop/B94D0EE58DD307E547C9D9D6A536D49F.jpg"))
    }
}
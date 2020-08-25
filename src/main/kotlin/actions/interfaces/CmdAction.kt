package actions.interfaces

import net.mamoe.mirai.message.GroupMessageEvent

/**
 * 继承这个方法
 */
interface CmdAction : GroupAction {

    /**
     * 命令的前缀, 示例: `/help`
     */
    val prefix: String

    /**
     * 帮助命令的文本
     */
    fun helperText(): String

}
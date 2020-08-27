package actions.interfaces

/**
 * 继承这个方法
 */
interface CmdAction : GroupMessageAction {

    /**
     * 命令的前缀, 示例: `/help`
     */
    val prefix: String

    fun prefixAlias(): List<String> {
        return emptyList()
    }

    fun showHelperText(): Boolean {
        return true
    }

    /**
     * 帮助命令的文本
     */
    fun helperText(): String


}

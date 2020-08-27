package qqbot.actions.group.cmd.admin

import net.mamoe.mirai.closeAndJoin
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.contact.nameCardOrNick
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import qqbot.bot
import qqbot.db.table.DenyTable
import qqbot.relaunchBot
import java.text.SimpleDateFormat
import java.util.*

// 重新登录, 以处理某些特殊情况
object LoginAction : AdminCmdAction {

    override fun showHelperText(): Boolean {
        return false
    }

    override val prefix: String
        get() = "/relogin"

    override fun helperText(): String {
        return "/relogin 有时候会有某些异常情况, 重新登录刷新下试试"
    }

    override suspend fun onAdminInvoke(event: GroupMessageEvent, params: String) {
        println("重新登录")
//        relaunchBot()
    }

}
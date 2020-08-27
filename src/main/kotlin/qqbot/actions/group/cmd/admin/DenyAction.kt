package qqbot.actions.group.cmd.admin

import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.contact.nameCardOrNick
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import qqbot.db.table.DenyTable
import java.text.SimpleDateFormat
import java.util.*

object DenyAction : AdminCmdAction {

    override val prefix: String
        get() = "/deny"

    override fun helperText(): String {
        return """/deny 拒绝名单功能
            |/deny add @xxx 踢出群, 并在再次入群后会自动踢出
            |/deny addQQ <QQ> 踢出群, 并在再次入群后会自动踢出
            |/deny remove QQ号 将某人移除出拒绝名单
            |/deny list
        """.trimMargin().trim()
    }

    override suspend fun onAdminInvoke(event: GroupMessageEvent, params: String) {
        val paramList = params.split(" ")
        when (paramList[0]) {
            "add" -> {
                add(event, paramList)
            }
            "addQQ" -> {
                addQQ(event, paramList)
            }
            "remove" -> {
                remove(event, paramList)
            }
            "list" -> {
                showList(event)
            }
            else -> {
                event.quoteReply("暂不支持")
            }
        }
    }

    private suspend fun showList(event: GroupMessageEvent) {
        var text: String = ""
        transaction {
            val qqGroup = event.group.id
            DenyTable.apply {
                val all = select {
                    group eq qqGroup
                }

                if (all.count() == 0L) {
                    text = "没记录"
                    return@transaction
                }

                text = all.joinToString("\n") { row ->
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                    val date = Date(row[insertDate])
                    val dateStr = sdf.format(date)
                    "qq: ${row[qq]}, 加入日期: $dateStr"
                }
            }
        }

        event.quoteReply(text)
    }

    private suspend fun remove(event: GroupMessageEvent, paramList: List<String>) {
        val groupId = event.group.id

        val ids = paramList.mapNotNull {
            it.toLongOrNull()
        }

        transaction {
            for (id in ids) {
                DenyTable.apply {
                    deleteWhere {
                        group eq groupId
                        qq eq id
                    }
                }
            }
        }

        event.quoteReply("移除成功")
    }

    private suspend fun add(event: GroupMessageEvent, paramList: List<String>) {
        val targets = event.message
            .filterIsInstance<At>()
            .mapNotNull {
                event.group.getOrNull(it.target)
            }.filter {
                !it.isOperator()
            }

        addToDb(event, targets)
    }

    private suspend fun addQQ(event: GroupMessageEvent, paramList: List<String>) {
        val targets = paramList.mapNotNull {
            it.toLongOrNull()
        }

        val members = targets.mapNotNull {
            event.group.getOrNull(it)
        }

        val groupId = event.group.id
        val sender = event.sender

        transaction {
            DenyTable.apply {
                for (target in targets) {
                    insertIgnore {
                        it[qq] = target
                        it[group] = groupId
                        it[by] = sender.id
                        it[byNick] = sender.nameCardOrNick
                        it[insertDate] = System.currentTimeMillis()
                    }
                }
            }
        }

        val text = members.joinToString {
            "${it.nameCardOrNick}(${it.id})  -----"
        }

        for (target in members) {
            target.kick()
        }

        removeMessage(event, text)
    }

    private suspend fun addToDb(event: GroupMessageEvent, targets: List<Member>) {
        val groupId = event.group.id
        val sender = event.sender
        transaction {
            DenyTable.apply {
                for (target in targets) {
                    insertIgnore {
                        it[qq] = target.id
                        it[group] = groupId
                        it[by] = sender.id
                        it[byNick] = sender.nameCardOrNick
                        it[insertDate] = System.currentTimeMillis()
                    }
                }
            }
        }

        val text = targets.joinToString {
            "${it.nameCardOrNick}(${it.id})  -----"
        }

        for (target in targets) {
            target.kick()
        }

        removeMessage(event, text)
    }

    private suspend fun removeMessage(event: GroupMessageEvent, text: String) {
        event.quoteReply("$text 已被加入幸运名单, 将享受飞机票一张")
    }

}
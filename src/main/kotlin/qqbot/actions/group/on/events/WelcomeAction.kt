package qqbot.actions.group.on.events

import kotlinx.coroutines.delay
import qqbot.actions.interfaces.OnEventAction
import qqbot.constants.Constants
import net.mamoe.mirai.event.events.MemberJoinEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.buildMessageChain
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import qqbot.db.table.DenyTable

object WelcomeAction : OnEventAction<MemberJoinEvent> {

    override suspend fun invoke(event: MemberJoinEvent) {
        if (!Constants.all.contains(event.group.id)) {
            return
        }
        event.group.sendMessage(
            buildMessageChain {
                add(
                    "欢迎 "
                )
                add(At(event.member))
                add(
                    """ 加入 Flutter 大糖罐~🍭
                    |请尽快修改昵称格式为："昵称|城市|技术栈"，示例：「法的空间|上海|UWP」。未修改昵称要被禁言噢~⚠️"""
                        .trimMargin()
                )
            }
        )
//        checkDenyList(event) // TODO 等 1.3.0 再实装
    }

    private suspend fun checkDenyList(event: MemberJoinEvent) {
        val id = event.member.id
        val groupId = event.group.id

        var empty = false

        transaction {
            DenyTable.apply {
                val query = select {
                    group eq groupId
                    qq eq id
                }

                empty = query.empty()
            }
        }

        if (!empty) {
            event.group.sendMessage(
                buildMessageChain {
                    add("恭喜你, ")
                    add(At(event.member))
                    add(" 因为之前的优异表现, 将享受免费机票一张, 再见👏 👏")
                }
            )

            delay(3000)
            event.member.kick("欢送欢送")
        } else {
            event.group.sendMessage(
                buildMessageChain {
                    add(
                        "欢迎 "
                    )
                    add(At(event.member))
                    add(
                        """ 加入 Flutter 大糖罐~🍭
                        |请尽快修改昵称格式为："昵称|城市|技术栈"，示例：「法的空间|上海|UWP」。未修改昵称要被禁言噢~⚠️"""
                            .trimMargin()
                    )
                }
            )

        }
    }
}
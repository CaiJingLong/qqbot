package actions.group.on

import actions.interfaces.OnEventAction
import constants.Constants
import net.mamoe.mirai.event.events.MemberJoinEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.buildMessageChain

object WelcomeAction: OnEventAction<MemberJoinEvent> {

    suspend override fun invoke(event: MemberJoinEvent) {
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
    }
}
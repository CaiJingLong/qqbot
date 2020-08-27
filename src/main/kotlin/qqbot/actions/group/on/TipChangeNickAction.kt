package qqbot.actions.group.on

import qqbot.actions.interfaces.GroupFilterAction
import qqbot.constants.Constants
import kotlinx.coroutines.delay
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.contact.nameCardOrNick
import net.mamoe.mirai.message.GroupMessageEvent

class TipChangeNickAction : GroupFilterAction {

    override fun supportIds(): List<Long>? {
        return Constants.onlyBig
    }

    override fun blockQQ(): List<Long>? { // 不和指定的机器人互动, 以免没完没了
        return listOf(
            2300406668, // 小宝
            3090077983, // 祖安
            2357621290, // 小拉面
            2854196310, // Q群管家
        )
    }

    private val regex = Regex(".+\\|.+\\|.+")

    override suspend fun onInvoke(event: GroupMessageEvent) {
        if (event.sender.permission.isOperator()) { // 面对管理员, 直接怂
            return
        }

        val nick = event.sender.nameCardOrNick
        if (!regex.matches(nick)) {
            event.quoteReply(
                """不改名字就想说话，给👴好好按照“昵称|城市|技术栈|”改名！"""
            )
            if (event.permission.isOperator()) {
                event.sender.mute(1)
                delay(1000 * 15) // 15秒后解封
                event.sender.unmute()
            }
        }
    }


}
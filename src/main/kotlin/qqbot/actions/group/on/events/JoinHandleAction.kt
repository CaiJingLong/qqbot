package qqbot.actions.group.on.events

import qqbot.actions.interfaces.OnEventAction
import net.mamoe.mirai.event.events.MemberJoinRequestEvent

object JoinHandleAction : OnEventAction<MemberJoinRequestEvent> {

    override suspend fun invoke(event: MemberJoinRequestEvent) {
        // TODO 暂时有问题, 后面再改
        println("${event.groupId}, ${event.fromId}, ${event.message}")
    }
}
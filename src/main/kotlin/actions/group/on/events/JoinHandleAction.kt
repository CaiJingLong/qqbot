package actions.group.on.events

import actions.interfaces.OnEventAction
import net.mamoe.mirai.event.events.MemberJoinRequestEvent

object JoinHandleAction:OnEventAction<MemberJoinRequestEvent> {

    override suspend fun invoke(event: MemberJoinRequestEvent) {
        event.groupId
    }
}
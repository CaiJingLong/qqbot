package actions.group.on.events

import actions.interfaces.OnEventAction
import net.mamoe.mirai.event.events.GroupMuteAllEvent

object GroupMuteAction : OnEventAction<GroupMuteAllEvent> {

    override suspend fun invoke(event: GroupMuteAllEvent) {
    }

}
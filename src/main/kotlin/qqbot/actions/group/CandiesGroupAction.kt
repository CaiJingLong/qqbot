package qqbot.actions.group

import qqbot.actions.interfaces.GroupFilterAction

interface CandiesGroupAction : GroupFilterAction {

    override fun supportGroupIds(): List<Long>? {
        return listOf(
            906936101,
            656238669,
        )
    }
}
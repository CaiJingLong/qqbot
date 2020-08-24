package actions.group

import actions.interfaces.GroupFilterAction

interface CandiesGroupAction : GroupFilterAction {

    override fun ids(): List<Long> {
        return listOf(
            906936101,
            656238669
        )
    }
}
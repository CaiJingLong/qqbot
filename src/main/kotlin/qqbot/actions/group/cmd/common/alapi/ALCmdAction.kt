package qqbot.actions.group.cmd.common.alapi

import qqbot.actions.interfaces.CmdAction
import qqbot.constants.Constants

interface ALCmdAction : CmdAction {

    override fun supportGroupIds(): List<Long>? {
        return Constants.testGroups
    }

}